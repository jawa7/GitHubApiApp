package com.githubapp.repository

import androidx.annotation.WorkerThread
import com.githubapp.db.Download
import com.githubapp.db.DownloadDao

class DownloadRepository(private val downloadDao: DownloadDao) {

    suspend fun allDownloads() = downloadDao.getAllDownloads()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(item: Download) {
        downloadDao.insert(item)
    }
}