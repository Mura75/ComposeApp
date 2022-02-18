package com.example.composeapp.presentation.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val viewState = MutableStateFlow(MovieDetailState.empty())
    val movieState = viewState.asStateFlow()

    fun getMovie(id: Int) {
        viewModelScope.launch {
            val movie = movieRepository.getMovieDetail(id = id)
            delay(1000)
            viewState.emit(
                MovieDetailState(isLoading = false, movie = movie)
            )
        }
    }
}