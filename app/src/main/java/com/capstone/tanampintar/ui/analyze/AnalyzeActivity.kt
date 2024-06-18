package com.capstone.tanampintar.ui.analyze

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.viewModels
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isVisible
import com.capstone.tanampintar.R
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.data.network.response.DetectionResponse
import com.capstone.tanampintar.data.network.response.SoilResponse
import com.capstone.tanampintar.databinding.ActivityAnalyzeBinding
import com.capstone.tanampintar.factory.ViewModelFactory
import com.capstone.tanampintar.ui.detail.DetailActivity
import com.capstone.tanampintar.utils.getImageUri
import com.capstone.tanampintar.utils.reduceFileImage
import com.capstone.tanampintar.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class AnalyzeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAnalyzeBinding
    private var resultString = ""
    var id = ""

    private val viewModel: AnalyzeViewModel by viewModels<AnalyzeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var currentImageUri: Uri? = null

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

        binding.clear.setOnClickListener {
            reset()
        }

        binding.detailButton.setOnClickListener {
            if(resultString != "") {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra(DetailActivity.SOIL_ID, id)
                startActivity(intent)
            } else {
                showToast("Data tanah tidak valid")
            }
        }


        viewModel.result.observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                    binding.analyze.text = "Sedang diproses..."
                    binding.analyze.isEnabled = false
                }

                is ResultState.Success -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        showLoading(false)

                        setupDetectionResult(result.data)
                        binding.apply {
                            analyze.text = "Selesai"
                            gallery.isEnabled = false
                            camera.isEnabled = false
                        }
                    }, 3000)
                }

                is ResultState.Error -> {
                    showLoading(false)
                    binding.apply {
                        analyze.text = "Gagal memproses gambar"
                        gallery.isEnabled = false
                        camera.isEnabled = false
                    }
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

    private fun reset() {
        currentImageUri = null
        binding.apply {
            imageDetection.setImageResource(R.drawable.image_preview)
            binding.btnLayout.visibility = View.GONE
            analyze.text = "Analisa Gambar"
            result.visibility = View.GONE
            analyze.isEnabled = false
            gallery.isEnabled = true
            camera.isEnabled = true
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

        when(data.prediction) {
            0 -> {
                resultString = "Aluvial"
                id = "58ba724d-27cb-11ef-a133-42010a940003"
            }
            1 -> {
                resultString = "Black"
                id = "e822284d-27ca-11ef-a133-42010a940003"
            }
            2 -> {
                resultString = "Clay"
                id = "9626a9ca-27ce-11ef-a133-42010a940003"
            }
            3 -> {
                resultString = "RedSoild"
                id = "241c83d1-27cb-11ef-a133-42010a940003"
            }
            else -> ""  // Handle default case if needed
        }

        Log.d("id", id)

        if (currentImageUri != null) {
            binding.apply {
                result.apply {
                    isVisible = true
                    text = getString(R.string.result_analyze, resultString)
                }
                btnLayout.visibility = View.VISIBLE
            }
        }

    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}