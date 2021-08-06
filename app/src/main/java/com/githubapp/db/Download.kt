package com.githubapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "download_table")
data class Download(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "repoName") val repoName: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "language") val language: String?,
    @ColumnInfo(name = "date") val date: String?
)