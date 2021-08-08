package com.githubapp.presentation

import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.githubapp.R
import com.githubapp.presentation.components.CircularProgressBarAnimation
import com.githubapp.domain.model.GithubRepo
import java.io.File
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun SearchResults(
    navController: NavController,
    name: String,
    isNetworkAvailable: Boolean

) {
    val githubViewModel: GithubViewModel = hiltViewModel()

    val darkTheme: Boolean = isSystemInDarkTheme()

    val items = githubViewModel.repositories.value

    if (items.isEmpty()) {
        githubViewModel.newSearch(name)
    }

    val page = githubViewModel.page.value


    if (items.isEmpty()) {
        Scaffold(
            content = {
                if (isNetworkAvailable) {
                    NotValidWithDownloading(githubViewModel = githubViewModel)
                } else {
                    NoInternet()
                }
            },
            topBar = {
                TopAppBar {
                    AppBar(navController, name = "")
                }
            }
        )
    } else {
        Scaffold(
            content = {
                if (darkTheme) {
                    LazyColumn(

                    ) {
                        itemsIndexed(
                            items = items
                        ) { index, item ->
                            githubViewModel.onChangeScrollPosition(index)
                            if ((index + 1) >= (page * PAGE_SIZE)) {
                                githubViewModel.nextPage(name)
                            }
                            Repo(
                                item = item,
                                navController = navController,
                                author = name,
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .background(Color.Black.copy(0.1f))
                            .fillMaxSize()
                    ) {
                        itemsIndexed(
                            items = items
                        ) { index, item ->
                            githubViewModel.onChangeScrollPosition(index)
                            if ((index + 1) >= (page * PAGE_SIZE)) {
                                githubViewModel.nextPage(name)
                            }
                            Repo(
                                item = item,
                                navController = navController,
                                author = name,
                            )
                        }
                    }
                }

            },
            topBar = {
                TopAppBar {
                    AppBar(navController, "$name's")
                }
            }
        )
    }
}


@Composable
fun Repo(
    item: GithubRepo,
    navController: NavController,
    author: String,
) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, end = 12.dp, start = 12.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Row(
            ) {
                Text(
                    text = item.name,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 4.dp,
                            top = 12.dp
                        )
                        .weight(0.9f),
                    style = MaterialTheme.typography.body1
                )

                val link = URLEncoder.encode(item.html_url, "utf-8")
                IconButton(
                    onClick = { navController.navigate("webview/${link}") },
                    modifier = Modifier
                        .weight(0.1f)
                        .padding(end = 6.dp)
                ) {
                    Icon(
                        Icons.Default.Web,
                        contentDescription = "",
                        tint = LocalContentColor.current.copy(0.8f)
                    )
                }
            }

            if (item.description == null) {
                Text(
                    text = stringResource(R.string.no_description),
                    modifier = Modifier.padding(end = 8.dp, start = 8.dp, top = 6.dp),
                    style = MaterialTheme.typography.body2
                )
            } else {
                Text(
                    text = item.description,
                    modifier = Modifier.padding(end = 8.dp, start = 8.dp, top = 6.dp),
                    style = MaterialTheme.typography.body2
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box {
                    val githubViewModel: GithubViewModel = hiltViewModel()
                    val context = LocalContext.current
                    IconButton(
                        onClick = {
                            val path: File =
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + File.separator + item.name + ".zip")
                            if (path.isFile) {
                                Toast.makeText(
                                    context,
                                    item.name + R.string.exists,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else {
                                githubViewModel.downloading(context, author, item.name)
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    githubViewModel.insertD(
                                        author = author,
                                        repoName = item.name,
                                        description = item.description,
                                        language = item.language,
                                        date = SimpleDateFormat(
                                            "MM.dd",
                                            Locale.getDefault()
                                        ).format(Date())
                                    )
                                }
                            }
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Icon(
                            Icons.Default.FileDownload,
                            contentDescription = "",
                            tint = LocalContentColor.current.copy(0.8f)
                        )
                    }
                }
                Row(
                    modifier = Modifier.padding(top = 16.dp)

                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .size(20.dp),
                        tint = LocalContentColor.current.copy(0.8f)
                    )
                    Text(
                        text = item.stars.toString(),
                        modifier = Modifier
                            .padding(end = 8.dp, start = 4.dp, top = 8.dp),
                        style = MaterialTheme.typography.caption,
                    )
                    if (item.language == null) {
                        Text(
                            text = stringResource(R.string.unknown_language),
                            modifier = Modifier.padding(
                                end = 8.dp,
                                start = 12.dp,
                                top = 8.dp
                            ),
                            style = MaterialTheme.typography.caption,
                        )
                    } else {
                        Text(
                            text = item.language,
                            modifier = Modifier.padding(
                                end = 8.dp,
                                start = 12.dp,
                                top = 8.dp
                            ),
                            style = MaterialTheme.typography.caption,
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun AppBar(
    navController: NavController,
    name: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Row() {
            IconButton(
                onClick = {
                    navController.popBackStack(
                        "search_screen",
                        inclusive = false
                    )
                },
                modifier = Modifier
                    .padding(8.dp)
                    .size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ChevronLeft,
                    contentDescription = "",
                )
            }
            Text(
                name + " " + stringResource(R.string.repositories),
                fontWeight = FontWeight.W600,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(bottom = 4.dp)
            )
        }
        Box() {
            IconButton(
                onClick = { navController.navigate("download_screen") },
                modifier = Modifier
                    .padding(8.dp)
                    .size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Folder,
                    contentDescription = "",
                )
            }
        }
    }
}

@Composable
fun NotValidWithDownloading(
    githubViewModel: GithubViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressBarAnimation(isDisplayed = githubViewModel.loading.value)
        if (!githubViewModel.loading.value) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.login_is_not_valid)
            )
        }
    }
}

@Composable
fun NoInternet() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.no_internet_connection)
        )
    }
}
