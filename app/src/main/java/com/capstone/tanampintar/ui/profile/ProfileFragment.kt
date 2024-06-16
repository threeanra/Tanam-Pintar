package com.capstone.tanampintar.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.tanampintar.R
import com.capstone.tanampintar.data.local.pref.PreferencesHelper
import com.capstone.tanampintar.databinding.FragmentProfileBinding
import com.capstone.tanampintar.factory.ViewModelFactory
import com.capstone.tanampintar.ui.login.LoginActivity
import com.capstone.tanampintar.ui.splashscreen.AuthViewModel

class ProfileFragment : Fragment() {

    private var _profileBinding: FragmentProfileBinding? = null
    private val profileBinding get() = _profileBinding!!

    private val viewModel: AuthViewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private val settingPreferences by viewModels<SettingsViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _profileBinding = FragmentProfileBinding.inflate(inflater, container, false)

        preferencesHelper = PreferencesHelper(requireContext())

        settingPreferences.darkMode.observe(viewLifecycleOwner) { isDarkMode ->
            isDarkMode?.let {
                setDarkMode(it)
                profileBinding.switchMode.isChecked = it
            }
        }

        profileBinding.apply {
            switchMode.isChecked = preferencesHelper.getDarkMode()
            switchMode.setOnCheckedChangeListener { _, isChecked ->
                settingPreferences.setDarkMode(isChecked)
                preferencesHelper.setDarkMode(isChecked)
            }
            btnLogout.setOnClickListener {
                showLogoutDialog()
            }

            viewProfile.setOnClickListener {
                Toast.makeText(requireContext(), "Fitur yang akan datang", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getUser().observe(viewLifecycleOwner) { user ->
            profileBinding.nameProfile.text = user.name
            profileBinding.emailProfile.text = user.email
        }

        return profileBinding.root
    }

    private fun showLogoutDialog() {
        val dialogView = LayoutInflater.from(requireActivity()).inflate(R.layout.alert_dialog, null)

        val builder = AlertDialog.Builder(requireActivity())
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent) //to make alert dialog rounded corner

        dialogView.findViewById<Button>(R.id.btnYes).setOnClickListener {
            viewModel.logout()
            LoginActivity.start(requireContext())
            requireActivity().finish()
            alertDialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.btnNo).setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun setDarkMode(isEnable: Boolean) {
        if (isEnable) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _profileBinding = null
    }
}