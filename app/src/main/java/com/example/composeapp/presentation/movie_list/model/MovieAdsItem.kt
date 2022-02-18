package com.example.composeapp.presentation.movie_list.model

data class AdsGroupItem(
    val ads: List<MovieAdsItem>
) : ListItem

data class MovieAdsItem(
    val image: String
) : ListItem
