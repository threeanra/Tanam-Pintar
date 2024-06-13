package com.capstone.tanampintar.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.data.network.response.DetailSoilResponse
import com.capstone.tanampintar.data.network.response.PlantResponse
import com.capstone.tanampintar.data.network.response.Soil
import com.capstone.tanampintar.data.network.response.SoilResponse
import com.capstone.tanampintar.repository.PlantRepository
import com.capstone.tanampintar.repository.SoilRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val soilRepository: SoilRepository, private val plantRepository: PlantRepository) : ViewModel() {

    val detailSoil: LiveData<ResultState<DetailSoilResponse>> = soilRepository.detailSoil
    val plant: LiveData<ResultState<PlantResponse>> = plantRepository.plant

    init {
        getPlants()
    }

    fun getPlants(){
        viewModelScope.launch {
            plantRepository.getPlant()
        }
    }

    fun getDetailSoil(id: String) {
        viewModelScope.launch {
            soilRepository.getDetailSoil(id)
        }
    }
}