package com.example.composeapp.presentation.movie_detail

sealed class MovieDetailEvent {

    class MovieDetail(val id: Int) : MovieDetailEvent()
}