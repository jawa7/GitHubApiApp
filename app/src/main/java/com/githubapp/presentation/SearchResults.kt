package com.githubapp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Web
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.githubapp.CircularProgressBarAnimation
import com.githubapp.domain.model.GithubRepo
import java.net.URLEncoder

@Composable
fun SearchResults(
    navController: NavController,
    name: String

) {
    val githubViewModel: GithubViewModel = hiltViewModel()
    githubViewModel.newSearch(name)
    val items = githubViewModel.repositories.value
    Scaffold(
        content = {
            LazyColumn() {
                itemsIndexed(
                    items = items
                ) { index, item ->
                    Repo(
                        item = item,
                        navController = navController
                    )
                }
            }
        },
        topBar = {
            TopAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
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
                    Column {
                        Text(
                            name,
                            fontWeight = FontWeight.W600
                        )
                        Text(
                            "Repositories",
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        }
    )
}


@Composable
fun Repo(
    item: GithubRepo,
    navController: NavController,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column() {
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

            item.description?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(end = 8.dp, start = 8.dp, top = 6.dp),
                    style = MaterialTheme.typography.body2
                )
            }


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box() {
                    IconButton(
                        onClick = { },
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
                    item.language?.let {
                        Text(
                            text = it,
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

