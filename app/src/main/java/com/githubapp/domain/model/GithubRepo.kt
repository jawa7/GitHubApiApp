package com.githubapp.domain.model


data class GithubRepo (
    val name: String,
    val full_name: String,
    val private: Boolean,
    val html_url: String,
    val description: String?,
    val download_url: String?,
    val language: String?,
    val stars: Int,
)