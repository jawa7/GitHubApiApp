package com.githubapp.domain.model


data class GithubRepo (
    val name: String,
    val html_url: String,
    val description: String?,
    val language: String?,
    val stars: Int,
)