package com.githubapp

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.compose.navArgument
import com.githubapp.presentation.screens.DownloadScreen
import com.githubapp.presentation.SearchResults
import com.githubapp.presentation.SearchScreen
import com.githubapp.presentation.components.WebView
import com.githubapp.ui.theme.GitHubAppTheme
import com.githubapp.util.ConnManager
import com.githubapp.util.ConnectionLiveData
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
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
                val navController = rememberAnimatedNavController()
                var dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                Surface(color = MaterialTheme.colors.background) {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = "search_screen",
                    ) {
                        composable(
                            "search_screen",
                            exitTransition = { _,_ ->
                                slideOutHorizontally(
                                    targetOffsetX = {-300},
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        easing = FastOutSlowInEasing
                                    )
                                ) + fadeOut(animationSpec = tween(300))
                            },
                            popEnterTransition = {_,_ ->
                                slideInHorizontally(
                                    initialOffsetX = {-300},
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        easing = FastOutSlowInEasing
                                    )
                                ) + fadeIn(animationSpec = tween(300))
                            }
                        ) {
                            SearchScreen(
                                navController = navController
                            )
                        }
                        composable(
                            "search_results/{name}",
                            arguments = listOf(
                                navArgument("name") { type = NavType.StringType }
                            ),
                            enterTransition = { _,_ ->
                                slideInHorizontally(
                                    initialOffsetX = {-300},
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        easing = FastOutSlowInEasing
                                    )
                                ) + fadeIn(animationSpec = tween(300))
                            },
                            popExitTransition = {_,_ ->
                                slideOutHorizontally(
                                    targetOffsetX = {300},
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        easing = FastOutSlowInEasing
                                    )
                                ) + fadeOut(animationSpec = tween(300))
                            }
                        ) { navBackStackEntry ->
                            navBackStackEntry.arguments?.getString("name")?.let {
                                    SearchResults(
                                        navController = navController,
                                        name = it,
                                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value
                                    )
                                }
                            }

                        composable(
                            "webview/{url}",
                            arguments = listOf(
                                navArgument("url") { type = NavType.StringType },
                            )
                        ) { navBackStackEntry ->
                                WebView(
                                    navController = navController,
                                    url = navBackStackEntry.arguments?.getString("url")
                                )
                            }

                        composable("download_screen") {
                                DownloadScreen(
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }


    }
}

