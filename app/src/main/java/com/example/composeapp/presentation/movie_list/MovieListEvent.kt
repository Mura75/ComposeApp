package com.example.composeapp.presentation.movie_list

sealed class MovieListEvent {

    object Refresh : MovieListEvent()

    object LoadMore : MovieListEvent()

    class SaveState(val lastPosition: Int, val scrollOffset: Int) : MovieListEvent()
}
