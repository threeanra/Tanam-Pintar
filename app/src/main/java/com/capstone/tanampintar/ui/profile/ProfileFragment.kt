package com.capstone.tanampintar.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.tanampintar.R
import com.capstone.tanampintar.databinding.FragmentProfileBinding
import com.capstone.tanampintar.factory.ViewModelFactory
import com.capstone.tanampintar.ui.login.LoginActivity
import com.capstone.tanampintar.ui.splashscreen.AuthViewModel

class ProfileFragment : Fragment() {

    private var _profileBinding: FragmentProfileBinding? = null
    private val profileBinding get() = _profileBinding!!

    private val viewModel: AuthViewModel by viewModels<AuthViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _profileBinding = FragmentProfileBinding.inflate(inflater, container, false)

        profileBinding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }


        return profileBinding.root
//        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun showLogoutDialog() {
        val dialogView =
            LayoutInflater.from(this.requireActivity()).inflate(R.layout.alert_dialog, null)

        val builder = AlertDialog.Builder(this.requireActivity())
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = builder.create()

        dialogView.findViewById<Button>(R.id.btnYes).setOnClickListener {
            viewModel.logout()
            LoginActivity.start(requireContext())
            activity?.finish()
        }
        dialogView.findViewById<Button>(R.id.btnNo).setOnClickListener {
            alertDialog.dismiss()
        }


        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _profileBinding = null
    }
}