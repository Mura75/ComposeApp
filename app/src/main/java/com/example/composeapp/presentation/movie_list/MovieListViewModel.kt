package com.example.composeapp.presentation.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.domain.model.Movie
import com.example.composeapp.domain.model.PageInfo
import com.example.composeapp.domain.use_case.GetMoviesUseCase
import com.example.composeapp.presentation.movie_list.model.AdsGroupItem
import com.example.composeapp.presentation.movie_list.model.ListItem
import com.example.composeapp.presentation.movie_list.model.MovieAdsItem
import com.example.composeapp.presentation.movie_list.model.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val viewState = MutableStateFlow(MovieListState.empty())
    val listState = viewState.asStateFlow()

    private var state: MovieListState = MovieListState.empty()

    init {
        getMovies()
    }

    fun onEvent(event: MovieListEvent) {
        when (event) {
            MovieListEvent.Refresh -> refresh()
            MovieListEvent.LoadMore -> getMovies()
            is MovieListEvent.SaveState -> saveState(lastPosition = event.lastPosition, scrollOffset = event.scrollOffset)
        }
    }

    private fun getMovies() {
        viewModelScope.launch {
            val pageInfo = getMoviesUseCase.execute(page = state.nextPage)
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

    private fun refresh() {
        viewModelScope.launch {
            updateState(
                state = MovieListState.empty()
            )
            val pageInfo = getMoviesUseCase.execute(page = state.nextPage)
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

    private fun saveState(lastPosition: Int, scrollOffset: Int) {
        viewModelScope.launch {
            viewState.emit(
                state.copy(
                    lastPosition = lastPosition,
                    scrollOffset = scrollOffset
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