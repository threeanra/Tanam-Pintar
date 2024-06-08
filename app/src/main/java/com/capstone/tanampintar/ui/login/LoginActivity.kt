package com.capstone.tanampintar.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isInvisible
import com.capstone.tanampintar.R
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.databinding.ActivityLoginBinding
import com.capstone.tanampintar.databinding.ActivityMainBinding
import com.capstone.tanampintar.factory.ViewModelFactory
import com.capstone.tanampintar.ui.MainActivity
import com.capstone.tanampintar.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.register.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.login.setOnClickListener {
            if (binding.edLoginEmail.text!!.isNotEmpty() && binding.edPasswordEmail.text!!.isNotEmpty()) {
                viewModel.submitLogin(
                    email = binding.edLoginEmail.text.toString(),
                    password = binding.edPasswordEmail.text.toString()
                )
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Please fill the form correctly",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.responseResult.observe(this@LoginActivity) { response ->
            when (response) {
                is ResultState.Loading -> {
                    showLoading()
                }
                is ResultState.Error -> {
                    binding.loading.visibility = View.GONE
                    binding.register.isEnabled = true
                    binding.login.isInvisible = false
                    Toast.makeText(
                        this@LoginActivity,
                        response.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ResultState.Success -> {
                    val homeActivity = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(homeActivity)
                    finish()
                }
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            login.isInvisible = true
            register.isEnabled = false
            loading.visibility = View.VISIBLE
        }
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, LoginActivity::class.java)
            starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            starter.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            context.startActivity(starter)
        }
    }
}