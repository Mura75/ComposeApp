package com.example.composeapp.presentation.movie_list.model

import com.example.composeapp.domain.model.Movie

data class MovieItem(
    val movie: Movie
) : ListItem
