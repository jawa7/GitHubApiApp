package com.githubapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.githubapp.presentation.SearchResults
import com.githubapp.presentation.SearchScreen
import com.githubapp.presentation.components.WebView
import com.githubapp.ui.theme.GitHubAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var app: BaseApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubAppTheme {
                val navController = rememberNavController()
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
                                SearchResults(
                                    navController = navController,
                                    name = it
                                )
                            }
                        }
                        composable(
                            "webview/{url}",
                            arguments = listOf(
                                navArgument("url") { type = NavType.StringType }
                            )
                        ) { navBackStackEntry ->
                            WebView(
                                navController = navController,
                                author = "mitchtabian",
                                url = navBackStackEntry.arguments?.getString("url")
                            )

                        }
                    }
                }
            }
        }
    }
}

