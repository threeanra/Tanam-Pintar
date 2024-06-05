package com.capstone.tanampintar.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.capstone.tanampintar.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _profileBinding: FragmentProfileBinding? = null
    private val profileBinding get() = _profileBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _profileBinding = FragmentProfileBinding.inflate(inflater, container, false)

        profileBinding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }

        return profileBinding.root
//        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun showLogoutDialog() {
        val alertDialog = AlertDialog.Builder(this.requireActivity())
        alertDialog.setTitle("Logout")
            .setMessage("Apakah Kamu Yakin Ingin Logout?")
            .setPositiveButton("Iya") { dialog, _ ->
                // Handle Logic Code Logout
                dialog.dismiss()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = alertDialog.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _profileBinding = null
    }
}