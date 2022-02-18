package com.example.composeapp.domain.model

data class PageInfo(
    val nextPage: Int,
    val movies: List<Movie>,
    val ads: List<String>,
    val isPageEnd: Boolean
)