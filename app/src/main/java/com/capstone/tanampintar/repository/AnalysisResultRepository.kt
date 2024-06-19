package com.capstone.tanampintar.repository

import androidx.lifecycle.LiveData
import com.capstone.tanampintar.data.local.room.AnalysisResult
import com.capstone.tanampintar.data.local.room.AnalysisResultDao
import com.capstone.tanampintar.data.local.room.HistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnalysisResultRepository private constructor(private val analysisResultDao: AnalysisResultDao) {

    fun getAllAnalysisResults(): LiveData<List<AnalysisResult>> =
        analysisResultDao.getAllHistory()

    suspend fun insertAnalysisResult(analysisResult: AnalysisResult) {
        analysisResultDao.insert(analysisResult)
    }

    suspend fun deleteAnalysisResult(analysisResult: AnalysisResult) {
        analysisResultDao.delete(analysisResult)
    }

    companion object {
        @Volatile
        private var instance: AnalysisResultRepository? = null

        fun getInstance(analysisResultDao: AnalysisResultDao): AnalysisResultRepository =
            instance ?: synchronized(this) {
                instance ?: AnalysisResultRepository(analysisResultDao).also { instance = it }
            }
    }
}