package com.example.composeapp.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.domain.model.Movie
import com.example.composeapp.domain.model.PageInfo
import com.example.composeapp.domain.repository.MovieRepository
import com.example.composeapp.presentation.movie.model.AdsGroupItem
import com.example.composeapp.presentation.movie.model.ListItem
import com.example.composeapp.presentation.movie.model.MovieAdsItem
import com.example.composeapp.presentation.movie.model.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val viewState = MutableSharedFlow<MovieListState>()
    val movieState = viewState.asSharedFlow()

    private var state: MovieListState = MovieListState.empty()

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            val pageInfo = movieRepository.getMovies(page = state.nextPage)
            val items = state.items + pageInfo.moviesWithAds()
            updateState(
                state = state.copy(
                    items = items,
                    nextPage = pageInfo.nextPage,
                    isLoading = false
                )
            )
        }
    }

    fun refresh() {
        viewModelScope.launch {
            updateState(
                state = MovieListState.empty()
            )
            val pageInfo = movieRepository.getMovies(page = state.nextPage)
            val items = state.items + pageInfo.moviesWithAds()

            updateState(
                state = state.copy(
                    items = items,
                    nextPage = pageInfo.nextPage,
                    isLoading = false
                )
            )
        }
    }

    private suspend fun updateState(state: MovieListState) {
        this.state = state
        viewState.emit(this.state)
    }

    private fun Movie.toMovieItem(): MovieItem {
        return MovieItem(this)
    }

    private fun PageInfo.moviesWithAds(): List<ListItem> {
        val list = ArrayList<ListItem>()
        movies.forEachIndexed { index, movie ->
            if ((index + 1) % 10 == 0) {
                list.add(AdsGroupItem(ads = ads.map { MovieAdsItem(it) }))
            }
            list.add(movie.toMovieItem())
        }
        return list
    }
}