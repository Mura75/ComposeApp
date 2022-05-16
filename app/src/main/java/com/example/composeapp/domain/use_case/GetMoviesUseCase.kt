package com.example.composeapp.domain.use_case

import com.example.composeapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun execute(page: Int) = movieRepository.getMovies(page)
}