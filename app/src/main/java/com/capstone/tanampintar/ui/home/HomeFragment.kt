package com.capstone.tanampintar.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.tanampintar.adapter.SoilAdapter
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.data.network.response.SoilResponse
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

    private val soilViewModel: HomeViewModel by viewModels<HomeViewModel>{
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
        setupViewModel()

       return homeBinding.root
    }

    private fun setupViewModel() {
        soilViewModel.soils.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ResultState.Loading -> {
                }

                is ResultState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ResultState.Success -> {
                    setRecyclerView(response.data)
                }
            }
        }
    }

    private fun setRecyclerView(items: SoilResponse) {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        homeBinding.rvSoilType.layoutManager = layoutManager
        val adapter = SoilAdapter(items.data)
        homeBinding.rvSoilType.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _homeBinding = null
    }

}
