package com.githubapp.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githubapp.domain.model.GithubRepo
import com.githubapp.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val repository: GithubRepository,
) : ViewModel() {

    val name = mutableStateOf("")

    val repositories: MutableState<List<GithubRepo>> = mutableStateOf(listOf())
    val loading = mutableStateOf(false)


    fun newSearch(name: String) {
        viewModelScope.launch {
            loading.value = true

            val result = repository.search(
                userName = name,
            )
            loading.value = false

            repositories.value = result
        }
    }
}
