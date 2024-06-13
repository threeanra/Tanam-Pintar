package com.capstone.tanampintar.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.data.network.response.DetailSoilResponse
import com.capstone.tanampintar.data.network.response.Soil
import com.capstone.tanampintar.data.network.response.SoilResponse
import com.capstone.tanampintar.repository.SoilRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val soilRepository: SoilRepository) : ViewModel() {

    val detailSoil: LiveData<ResultState<DetailSoilResponse>> = soilRepository.detailSoil

    fun getDetailSoil(id: String) {
        viewModelScope.launch {
            soilRepository.getDetailSoil(id)
        }
    }
}