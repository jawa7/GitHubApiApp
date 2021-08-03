package com.githubapp.repository

import com.githubapp.domain.model.GithubRepo

interface GithubRepository {

    suspend fun search(userName: String): List<GithubRepo>

}