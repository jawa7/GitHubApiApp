package com.githubapp.presentation

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val repository: GithubRepository,
    //   @Named("access_token") private val access_token: String,
    private var connectivityManager: ConnManager,
    private val downloadRepository: DownloadRepository
) : ViewModel() {

    val repositories: MutableState<List<GithubRepo>> = mutableStateOf(ArrayList())
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    var scrollPosition = 0


    fun newSearch(name: String) {
        if (connectivityManager.isNetworkAvailable.value) {

            viewModelScope.launch {
                loading.value = true
                try {
                    val result = repository.search(
                    //    access_token = access_token,
                        userName = name,
                        page = 1,
                    )
                    repositories.value = result
                    delay(2000)
                } catch (e: HttpException) {
                    repositories.value = emptyList()
                }
                loading.value = false
            }
        }
    }

    fun nextPage(name: String) {
        viewModelScope.launch {
            Log.d("MainActivity", "nextPage: ${scrollPosition}")
            if ((scrollPosition + 1) >= (page.value * PAGE_SIZE)) {
                loading.value = true
                incrementPage()
                Log.d("MainActivity", "nextPage: triggered ${page.value}")
                delay(1000)
                if(page.value > 1) {
                    val result = repository.search(
                        userName = name,
                        page = page.value,
                    //    access_token = access_token
                    )
                    appendRepos(result)
                }
                loading.value = false
            }
        }
    }

    private fun appendRepos(repos: List<GithubRepo>){
        val current = ArrayList(this.repositories.value)
        current.addAll(repos)
        repositories.value = current
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onChangeScrollPosition(position: Int) {
        scrollPosition = position
    }

    fun downloading(context: Context, author: String, repoName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = DownloadManager.Request(
                Uri.parse("https://api.github.com/repos/${author}/${repoName}/zipball/master")
            )
                .setTitle(repoName)
                .setDescription(author)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, repoName)
            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)

        }
    }

    fun allDownloads(): List<Download> = runBlocking {
        downloadRepository.allDownloads()
    }

    private fun insert(item: Download) = runBlocking {
        downloadRepository.insert(item)
    }
    fun insertD(author: String, repoName: String, description: String?, language: String?, date: String) {
        if (author.isNotEmpty() && repoName.isNotEmpty()) {
            val download = Download(
                author = author,
                repoName = repoName,
                description = description,
                language = language,
                date = date
            )
            insert(download)
        }
    }
}
