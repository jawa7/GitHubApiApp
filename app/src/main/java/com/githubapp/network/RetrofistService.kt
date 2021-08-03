package com.githubapp.network

import com.githubapp.network.model.GithubDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("users/{user}/repos?page=number")
    suspend fun search(
        @Path("user")  user: String,
    ): List<GithubDto>
}