package com.example.composeapp.presentation.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.domain.use_case.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val viewState = MutableStateFlow(MovieDetailState.empty())
    val movieState = viewState.asStateFlow()

    fun onEvent(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailEvent.MovieDetail -> getMovie(id = event.id)
        }
    }

    private fun getMovie(id: Int) {
        viewModelScope.launch {
            val movie = getMovieDetailsUseCase.execute(id = id)
            delay(1000)
            viewState.emit(
                MovieDetailState(isLoading = false, movie = movie)
            )
        }
    }
}