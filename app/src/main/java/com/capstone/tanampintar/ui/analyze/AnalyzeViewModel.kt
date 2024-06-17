package com.capstone.tanampintar.ui.analyze

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.data.network.response.DetectionResponse
import com.capstone.tanampintar.repository.DetectionRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class AnalyzeViewModel(private val repository: DetectionRepository): ViewModel() {

    val result: LiveData<ResultState<DetectionResponse>> = repository.result

    fun uploadImage(image: MultipartBody.Part) {
        viewModelScope.launch {
            repository.uploadImage(image)
        }
    }
}