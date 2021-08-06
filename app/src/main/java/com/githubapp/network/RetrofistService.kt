package com.githubapp.network

import com.githubapp.network.model.GithubDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Url


interface GithubService {
    @GET("users/{user}/repos")
    suspend fun search(
    //    @Header("Authorization") access_token: String,
        @Path("user")  user: String,
    ): List<GithubDto>
}