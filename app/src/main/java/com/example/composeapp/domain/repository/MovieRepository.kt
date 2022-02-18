package com.example.composeapp.domain.repository

import com.example.composeapp.domain.model.PageInfo

interface MovieRepository {

    suspend fun getMovies(page: Int): PageInfo
}