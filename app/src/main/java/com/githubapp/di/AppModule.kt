package com.githubapp.di

import android.content.Context
import androidx.room.Room
import com.githubapp.BaseApplication
import com.githubapp.db.DownloadDao
import com.githubapp.db.DownloadDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Provides
    fun provideDownloadDao(appDatabase: DownloadDatabase) : DownloadDao {
        return appDatabase.downloadDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context):
            DownloadDatabase {
        return Room.databaseBuilder(
            appContext,
            DownloadDatabase::class.java,
            "download_database"
        ).build()
    }
}