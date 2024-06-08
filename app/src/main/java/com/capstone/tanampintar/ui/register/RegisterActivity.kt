package com.capstone.tanampintar.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.capstone.tanampintar.R
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.databinding.ActivityRegisterBinding
import com.capstone.tanampintar.factory.ViewModelFactory
import com.capstone.tanampintar.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            login.setOnClickListener {
                LoginActivity.start(this@RegisterActivity)
            }
            register.setOnClickListener {
                if (edEmail.text!!.isNotEmpty() && edFullname.text!!.isNotEmpty() && edPassword.text?.length!! >= 8) {
                    viewModel.submitRegister(
                        name = edFullname.text.toString(),
                        email = edEmail.text.toString(),
                        password = edPassword.text.toString()
                    )
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please fill the form correctly",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.responseResult.observe(this@RegisterActivity) {
                response ->
            when (response) {
                is ResultState.Loading -> {
                    showLoading()
                }
                is ResultState.Error -> {
                    binding.loading.visibility = View.GONE
                    binding.login.isEnabled = true
                    binding.register.isInvisible = false
                    Toast.makeText(
                        this@RegisterActivity,
                        response.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ResultState.Success -> {
                    Toast.makeText(this@RegisterActivity, getString(R.string.email_created), Toast.LENGTH_SHORT).show()
                    val homeActivity = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(homeActivity)
                    finish()
                }
            }
        }

    }

    private fun showLoading() {
        binding.apply {
            register.isInvisible = true
            login.isEnabled = false
            loading.visibility = View.VISIBLE
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, RegisterActivity::class.java)
            context.startActivity(starter)
        }
    }
}