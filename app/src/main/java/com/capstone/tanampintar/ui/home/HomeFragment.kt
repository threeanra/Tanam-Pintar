package com.capstone.tanampintar.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.capstone.tanampintar.databinding.FragmentHomeBinding
import com.capstone.tanampintar.factory.ViewModelFactory
import com.capstone.tanampintar.ui.detail.DetailActivity
import com.capstone.tanampintar.ui.history.HistoryActivity
import com.capstone.tanampintar.ui.login.LoginActivity
import com.capstone.tanampintar.ui.splashscreen.AuthViewModel

class HomeFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels<AuthViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }
    private var _homeBinding: FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _homeBinding = FragmentHomeBinding.inflate(inflater,container,false)

        homeBinding.historyCard.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }

        homeBinding.reminderCard.setOnClickListener {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            startActivity(intent)
        }

        viewModel.getUser().observe(viewLifecycleOwner) {
            if (it.token.isEmpty()) {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            } else {
                homeBinding.fullname.text = it.name
            }
        }

       return homeBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _homeBinding = null
    }

}
