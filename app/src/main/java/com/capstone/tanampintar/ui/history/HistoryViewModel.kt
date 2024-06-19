package com.capstone.tanampintar.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.tanampintar.data.local.room.AnalysisResult
import com.capstone.tanampintar.repository.AnalysisResultRepository

class HistoryViewModel(private val repository: AnalysisResultRepository) : ViewModel() {

    val historyList: LiveData<List<AnalysisResult>> = repository.getAllAnalysisResults()
    suspend fun deleteAnalysisResult(analysisResult: AnalysisResult) {
        repository.deleteAnalysisResult(analysisResult)
    }

}