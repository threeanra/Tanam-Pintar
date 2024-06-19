package com.capstone.tanampintar.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnalysisResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(analysisResult: AnalysisResult)

    @Delete
    suspend fun delete(analysisResult: AnalysisResult)

    @Query("SELECT * FROM analysis_results")
    fun getAllHistory(): LiveData<List<AnalysisResult>>


}