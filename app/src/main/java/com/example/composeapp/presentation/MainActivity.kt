package com.example.composeapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composeapp.presentation.movie_detail.MovieDetailView
import com.example.composeapp.presentation.movie_list.MovieListView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp()
        }
    }
}


@Composable
fun MovieApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MovieList.name
    ) {
        composable(route = Screen.MovieList.name) {
            MovieListView(navController = navController)
        }
        composable(
            route = "${Screen.MovieDetail.name}?movie_id={movie_id}&movie_name={movie_name}",
            arguments = listOf(
                navArgument("movie_id") {
                    type = NavType.IntType
                    nullable = false
                },
                navArgument("movie_name") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->
            val movieId = requireNotNull(entry.arguments?.getInt("movie_id"))
            val movieName = requireNotNull(entry.arguments?.getString("movie_name"))
            MovieDetailView(movieId = movieId, name = movieName)
        }
    }
}

