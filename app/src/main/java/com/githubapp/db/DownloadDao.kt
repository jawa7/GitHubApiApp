package com.githubapp.db

import androidx.room.*

@Dao
interface DownloadDao {

    @Query("SELECT * FROM download_table ORDER BY id DESC")
    suspend fun getAllDownloads(): List<Download>

    @Query("SELECT EXISTS (SELECT author FROM download_table LIMIT 1)")
    suspend fun exists() : Boolean

    @Insert
    suspend fun insert(item : Download)

    @Update
    suspend fun update(item: Download)
}