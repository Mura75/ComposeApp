package com.example.composeapp.presentation.movie

import com.example.composeapp.presentation.movie.model.ListItem

data class MovieListState(
    val items: List<ListItem> = emptyList(),
    val isLoading: Boolean = true,
    val isLoadingMoreEnd: Boolean = false,
    val nextPage: Int = 1,
    val error: String? = null
) {

    companion object {
        fun empty(): MovieListState = MovieListState()
    }
}
