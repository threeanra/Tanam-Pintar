package com.capstone.tanampintar.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.tanampintar.R
import com.capstone.tanampintar.databinding.FragmentHomeBinding
import com.capstone.tanampintar.ui.history.HistoryActivity

class HomeFragment : Fragment() {

    private var _homeBinding: FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _homeBinding = FragmentHomeBinding.inflate(inflater,container,false)

        homeBinding.historyCard.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }

       return homeBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _homeBinding = null
    }

}
