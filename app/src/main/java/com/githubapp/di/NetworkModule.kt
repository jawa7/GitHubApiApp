package com.githubapp.di

import android.util.Log
import com.githubapp.network.model.GithubDtoMapper
import com.githubapp.network.GithubService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGithubDtoMapper(): GithubDtoMapper {
        return GithubDtoMapper()
    }

    @Singleton
    @Provides
    fun provideGithubService(): GithubService {

            return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(GithubService::class.java)
    }
    @Singleton
    @Provides
    @Named("access_token")
    fun provideAccessToken(): String {
        return "token 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"
    }
}