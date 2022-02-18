package com.example.composeapp.presentation.movie_detail

import com.example.composeapp.domain.model.Movie

data class MovieDetailState(
    val isLoading: Boolean = true,
    val movie: Movie? = null
) {

    companion object {
        fun empty() = MovieDetailState()
    }
}