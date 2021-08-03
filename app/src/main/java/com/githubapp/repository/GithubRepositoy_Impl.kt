package com.githubapp.repository

import com.githubapp.network.model.GithubDtoMapper
import com.githubapp.domain.model.GithubRepo
import com.githubapp.network.GithubService

class GithubRepositoy_Impl (
    private val githubService: GithubService,
    private val mapper: GithubDtoMapper,
): GithubRepository {

    override suspend fun search(userName: String): List<GithubRepo> {
        return mapper.toDomainList(githubService.search(userName))
    }
}
