package com.githubapp.presentation.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.githubapp.presentation.GithubViewModel

@Composable
fun Downloading(
    owner: String,
    repo: String,
) {
    val githubViewModel: GithubViewModel = hiltViewModel()
    // githubViewModel.newDownload(owner, repo)
}