package com.githubapp.presentation.components

import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import android.webkit.WebView
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.githubapp.R

@Composable
fun WebView(
    navController: NavController,
    url: String?,
) {
    Scaffold(
        content = {
            View(
                url = url
            )
        },
        topBar = {
            TopAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { navController.popBackStack("webview/{url}", inclusive = true) },
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
                        stringResource(R.string.repository),
                        fontWeight = FontWeight.W600,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(bottom = 4.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun View(
    url: String?
) {
    CircularProgressBarAnimation(isDisplayed = true)

    Column(modifier = Modifier.fillMaxHeight()) {
        AndroidView({ context ->
            val webView = WebView(context).apply {
                ViewGroup.LayoutParams.MATCH_PARENT
                ViewGroup.LayoutParams.MATCH_PARENT
            }
            webView.webViewClient = WebViewClient()

            url?.let { webView.loadUrl(it) }

            webView
        })
        CircularProgressBarAnimation(isDisplayed = false)
    }
}