package com.example.composeapp.data.movie.remote.model

import com.example.composeapp.domain.model.Genre
import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
) {

    fun mapToDomain(): Genre {
        return Genre(
            id, name
        )
    }
}