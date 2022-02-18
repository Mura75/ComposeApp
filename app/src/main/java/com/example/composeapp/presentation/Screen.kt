package com.example.composeapp.presentation

sealed class Screen(val name: String) {

    object MovieList : Screen(name = "movie_list")

    object MovieDetail : Screen(name = "movie_detail")

    fun withMovieId(movieId: Int, movieName: String): String {
        return buildString {
            append(name)
            append("?movie_id=$movieId")
            append("&movie_name=$movieName")
        }
    }
}
