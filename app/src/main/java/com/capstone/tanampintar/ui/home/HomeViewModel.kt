package com.capstone.tanampintar.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.data.network.response.SoilResponse
import com.capstone.tanampintar.repository.SoilRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val soilRepository: SoilRepository) : ViewModel() {

    val soils: LiveData<ResultState<SoilResponse>> = soilRepository.soil


    init {
        getSoil()
    }

    fun getSoil() {
        viewModelScope.launch {
            soilRepository.getSoil()
        }
    }
}