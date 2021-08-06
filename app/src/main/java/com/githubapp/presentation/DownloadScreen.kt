package com.githubapp.presentation


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.githubapp.db.Download


@Composable
fun DownloadScreen(
    navController: NavController,
) {
    val githubViewModel: GithubViewModel = hiltViewModel()
    val items = githubViewModel.allDownloads()

    Scaffold(
        content = {
            LazyColumn() {
                itemsIndexed(
                    items = items
                ) { index, item ->
                    Download(
                        item = item
                    )
                }
            }
        },
        topBar = {
            TopAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Row() {
                        IconButton(
                            onClick = {
                                navController.popBackStack("download_screen", inclusive = true)
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
                            "Downloads",
                            fontWeight = FontWeight.W600,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Box() {
                        IconButton(
                            onClick = {  navController.popBackStack(
                                "search_screen",
                                inclusive = false
                            ) },
                            modifier = Modifier
                                .padding(8.dp)
                                .size(30.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "",
                            )
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun Download(
    item: Download
) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, end = 12.dp, start = 12.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Row(
            ) {
                Text(
                    text = item.author!!,
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
            }
            if (item.description == null) {
                Text(
                    text = "No description",
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
                Row(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    if (item.language == null) {
                        Text(
                            text = "Unknown language",
                            modifier = Modifier.padding(
                                end = 8.dp,
                                start = 12.dp,
                                top = 8.dp,
                                bottom = 8.dp
                            ),
                            style = MaterialTheme.typography.caption,
                        )
                    } else {
                        Text(
                            text = item.language,
                            modifier = Modifier.padding(
                                end = 8.dp,
                                start = 12.dp,
                                top = 8.dp,
                                bottom = 8.dp
                            ),
                            style = MaterialTheme.typography.caption,
                        )
                    }
                }
                    Text(
                        text = item.date.toString(),
                        modifier = Modifier.padding(
                            end = 8.dp,
                            start = 12.dp,
                            top = 24.dp,
                            bottom = 8.dp
                        ),
                        style = MaterialTheme.typography.caption,
                    )
                }
            }
        }
    }
