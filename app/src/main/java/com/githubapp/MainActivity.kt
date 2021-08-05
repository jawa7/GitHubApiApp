package com.githubapp

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.githubapp.presentation.SearchResults
import com.githubapp.presentation.SearchScreen
import com.githubapp.presentation.components.Downloading
import com.githubapp.presentation.components.SlideLeftAnimation
import com.githubapp.presentation.components.WebView
import com.githubapp.ui.theme.GitHubAppTheme
import com.githubapp.util.ConnManager
import com.githubapp.util.ConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var app: BaseApplication

    @Inject
    lateinit var connectivityManager: ConnManager

    lateinit var connectionLiveData: ConnectionLiveData

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(this)
        setContent {
            GitHubAppTheme {
                val navController = rememberNavController()
                var dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                Surface(color = MaterialTheme.colors.background) {
                    NavHost(
                        navController = navController,
                        startDestination = "search_screen",
                    ) {
                        composable(
                            "search_screen",
                        ) {
                            SearchScreen(
                                navController = navController
                            )
                        }
                        composable(
                            "search_results/{name}",
                            arguments = listOf(
                                navArgument("name") { type = NavType.StringType }
                            )
                        ) { navBackStackEntry ->
                            navBackStackEntry.arguments?.getString("name")?.let {
                                SlideLeftAnimation {
                                    SearchResults(
                                        navController = navController,
                                        name = it,
                                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value
                                    )
                                }
                            }
                        }
                        composable(
                            "webview/{url}",
                            arguments = listOf(
                                navArgument("url") { type = NavType.StringType },
                            )
                        ) { navBackStackEntry ->
                            SlideLeftAnimation {
                                WebView(
                                    navController = navController,
                                    url = navBackStackEntry.arguments?.getString("url")
                                )
                            }
                        }
//                        composable(
//                            "download/{owner}/{repo}",
//                            arguments = listOf(
//                                navArgument("owner") { type = NavType.StringType },
//                                navArgument("repo") { type = NavType.StringType }
//                            ),
//                        ) { navBackStackEntry ->
//                            WebView(
//                                url = navBackStackEntry.arguments?.getString("owner")!! + navBackStackEntry.arguments?.getString("repo")!!,
//                                navController = navController
//                            )
//                        }
                    }
                }
            }
        }
    }
}

