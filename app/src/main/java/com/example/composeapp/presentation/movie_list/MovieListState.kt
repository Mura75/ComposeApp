package com.example.composeapp.presentation.movie_list

import com.example.composeapp.presentation.movie_list.model.ListItem

data class MovieListState(
    val items: List<ListItem> = emptyList(),
    val isLoading: Boolean = true,
    val isLoadingMoreEnd: Boolean = false,
    val nextPage: Int = 1,
    val error: String? = null,

    val lastPosition: Int = 0,
    val scrollOffset: Int = 0
) {

    companion object {
        fun empty(): MovieListState = MovieListState()
    }
}
