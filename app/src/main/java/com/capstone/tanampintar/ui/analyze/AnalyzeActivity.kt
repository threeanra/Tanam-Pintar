package com.capstone.tanampintar.ui.analyze

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.Manifest
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.data.network.response.DetectionResponse
import com.capstone.tanampintar.databinding.ActivityAnalyzeBinding
import com.capstone.tanampintar.factory.ViewModelFactory
import com.capstone.tanampintar.utils.getImageUri
import com.capstone.tanampintar.utils.reduceFileImage
import com.capstone.tanampintar.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class AnalyzeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAnalyzeBinding

    private val viewModel: AnalyzeViewModel by viewModels<AnalyzeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var currentImageUri: Uri? = null
    lateinit var resultString : String

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.imgBack.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        binding.camera.setOnClickListener {
            startCamera()
        }

        binding.gallery.setOnClickListener{
            startGallery()
        }

        binding.analyze.setOnClickListener{
            uploadImage()
        }

        viewModel.result.observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                    binding.analyze.isEnabled = false
                }

                is ResultState.Success -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        showLoading(false)
                        setupDetectionResult(result.data)
                    }, 3000)
                }

                is ResultState.Error -> {
                    showLoading(false)
                    Log.d("DetectionResponse", "Prediction: ${result.error}")
                }
            }
        }

    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.imageDetection.setImageURI(it)
            binding.analyze.isEnabled = true
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image Classification File", "showImage: ${imageFile.path}")
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )
            viewModel.uploadImage(multipartBody)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupDetectionResult(data: DetectionResponse) {
        if (currentImageUri != null) {
            binding.apply {
                result.apply {
                    isVisible = true
                    text = data.prediction.toString()
                }
            }
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}