package com.capstone.tanampintar.ui.history

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.tanampintar.data.local.room.AnalysisResult
import com.capstone.tanampintar.repository.AnalysisResultRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: AnalysisResultRepository) : ViewModel() {

    val historyList: LiveData<List<AnalysisResult>> = repository.getAllAnalysisResults()

    fun deleteAnalysisResult(analysisResult: AnalysisResult) {
        viewModelScope.launch {
            repository.deleteAnalysisResult(analysisResult)
        }
    }

}