package com.githubapp.di

import com.githubapp.network.model.GithubDtoMapper
import com.githubapp.repository.GithubRepository
import com.githubapp.repository.GithubRepositoy_Impl
import com.githubapp.network.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideGithubRepository(
        githubService: GithubService,
        githubDtoMapper: GithubDtoMapper,
    ): GithubRepository {
        return GithubRepositoy_Impl(
            githubService = githubService,
            mapper = githubDtoMapper,
        )
    }
}