package com.githubapp.repository

import com.githubapp.domain.model.GithubRepo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

interface GithubRepository {

    suspend fun search(access_token: String, userName: String): List<GithubRepo>
}