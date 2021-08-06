package com.githubapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Download::class], version = 1)
abstract class DownloadDatabase : RoomDatabase() {
    abstract fun downloadDao(): DownloadDao

}