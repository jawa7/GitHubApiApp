package com.githubapp.network.model

import com.google.gson.annotations.SerializedName

data class GithubDto (

    @SerializedName("name")
    val name: String,

    @SerializedName("full_name")
    val full_name: String,

    @SerializedName("private")
    val private: Boolean,

    @SerializedName("html_url")
    val html_url: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("download_url")
    val download_url: String?,

    @SerializedName("language")
    val language: String?,

    @SerializedName("stargazers_count")
    val stars: Int,

)