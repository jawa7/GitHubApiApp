package com.githubapp.presentation

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githubapp.db.Download
import com.githubapp.domain.model.GithubRepo
import com.githubapp.repository.DownloadRepository
import com.githubapp.repository.GithubRepository
import com.githubapp.util.ConnManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val repository: GithubRepository,
    @Named("access_token") private val access_token: String,
    private var connectivityManager: ConnManager,
    private val downloadRepository: DownloadRepository
) : ViewModel() {

    val repositories: MutableState<List<GithubRepo>> = mutableStateOf(listOf())
    val loading = mutableStateOf(false)

    fun newSearch(name: String) {
        if (connectivityManager.isNetworkAvailable.value) {

            viewModelScope.launch {
                loading.value = true
                try {
                    val result = repository.search(
                        access_token = access_token,
                        userName = name,
                    )
                    repositories.value = result

                } catch (e: HttpException) {
                    repositories.value = emptyList()
                }
                loading.value = false
            }
        }
    }

    fun downloading(context: Context, author: String, repoName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var downloadId: Long = 0
            val request = DownloadManager.Request(
                Uri.parse("https://api.github.com/repos/${author}/${repoName}/zipball/master")
            )
                .setTitle(repoName)
                .setDescription(author)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, repoName)
            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadId = dm.enqueue(request)

        }
    }

    fun allDownloads(): List<Download> = runBlocking {
        downloadRepository.allDownloads()
    }

    private fun insert(item: Download) = runBlocking {
        downloadRepository.insert(item)
    }
    fun insertD(author: String, repoName: String, description: String) {
        if (author.isNotEmpty() && repoName.isNotEmpty()) {
            val download = Download(
                author = author,
                repoName = repoName,
                description = description
            )
            insert(download)
        }
    }
}
