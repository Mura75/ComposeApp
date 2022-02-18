package com.example.composeapp.data.movie

import com.example.composeapp.data.movie.remote.MovieApi
import com.example.composeapp.domain.model.Movie
import com.example.composeapp.domain.model.PageInfo
import com.example.composeapp.domain.repository.MovieRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {

    override suspend fun getMovies(page: Int): PageInfo {
        val response = movieApi.getPopularMovies(page = page)
        val body = response.body()
        if (response.isSuccessful && body != null) {
            val currentPage = body.page ?: 0
            val totalPages = body.totalPages ?: 0
            val list = response.body()?.results?.map { it.mapToDomain() } ?: emptyList()

            delay(1000)
            return PageInfo(
                nextPage = page + 1,
                movies = list,
                ads = list.take(5).map { it.backdropPath.orEmpty() },
                isPageEnd = currentPage >= totalPages
            )
        } else {
            return PageInfo(
                nextPage = page,
                movies = emptyList(),
                ads = emptyList(),
                isPageEnd = false
            )
        }
    }

    override suspend fun getMovieDetail(id: Int): Movie? {
        val response = movieApi.getMovieDetail(movieId = id)
        val body = response.body()
        if (response.isSuccessful && body != null) {
            return body.mapToDomain()
        } else {
            return null
        }
    }
}