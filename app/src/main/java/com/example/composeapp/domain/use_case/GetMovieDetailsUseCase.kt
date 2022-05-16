package com.example.composeapp.domain.use_case

import com.example.composeapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun execute(id: Int) = movieRepository.getMovieDetail(id = id)
}