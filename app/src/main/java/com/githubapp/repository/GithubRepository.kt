package com.githubapp.repository

import com.githubapp.domain.model.GithubRepo

interface GithubRepository {

//      suspend fun search(userName: String, page: Int, access_token: String): List<GithubRepo>
      suspend fun search(userName: String, page: Int): List<GithubRepo>

}