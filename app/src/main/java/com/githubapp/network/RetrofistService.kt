package com.githubapp.network

import com.githubapp.network.model.GithubDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface GithubService {
    @GET("users/{user}/repos")
    suspend fun search(
    //    @Header("Authorization") access_token: String,
        @Path("user")  user: String,
    ): List<GithubDto>
}