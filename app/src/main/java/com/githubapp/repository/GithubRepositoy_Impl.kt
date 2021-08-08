package com.githubapp.repository

import com.githubapp.network.model.GithubDtoMapper
import com.githubapp.domain.model.GithubRepo
import com.githubapp.network.GithubService


class GithubRepositoy_Impl (
    private val githubService: GithubService,
    private val mapper: GithubDtoMapper,
): GithubRepository {

//    override suspend fun search(userName: String, page: Int, access_token: String): List<GithubRepo> {
//        return mapper.toDomainList(githubService.search(user = userName, page = page, access_token = access_token))
//    }

    override suspend fun search(userName: String, page: Int, ): List<GithubRepo> {
        return mapper.toDomainList(githubService.search(user = userName, page = page))
    }

    }

