package com.githubapp.db

import androidx.room.*

@Dao
interface DownloadDao {

    @Query("SELECT * FROM download_table ORDER BY id DESC")
    suspend fun getAllDownloads(): List<Download>

    @Insert
    suspend fun insert(item : Download)

}