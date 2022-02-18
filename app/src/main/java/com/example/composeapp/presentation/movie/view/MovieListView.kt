package com.example.composeapp.presentation.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composeapp.presentation.movie.model.AdsGroupItem
import com.example.composeapp.presentation.movie.model.MovieItem
import com.example.composeapp.presentation.movie.view.MovieList
import com.example.composeapp.presentation.theme.ComposeAppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MovieListView() {

    val movieListViewModel: MovieListViewModel = hiltViewModel()

    val state = movieListViewModel.movieState.collectAsState(
        initial = MovieListState.empty()
    )
    val isRefreshing = rememberSwipeRefreshState(state.value.isLoading)

    val movieList = state.value.items

    ComposeAppTheme(
        darkTheme = false
    ) {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Movies"
                        )
                    }
                )
                SwipeRefresh(
                    modifier = Modifier.fillMaxSize(),
                    state = isRefreshing,
                    onRefresh = { movieListViewModel.refresh() }
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(
                            vertical = 8.dp
                        )
                    ) {
                        item {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(
                                    vertical = 8.dp,
                                    horizontal = 8.dp
                                )
                            ) {
                                itemsIndexed(items = movieList) { index, item ->
                                    when(item) {
                                        is MovieItem -> {
                                            MovieList.PopularMovieItem(
                                                item = item,
                                                onItemClick = {}
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        itemsIndexed(items = movieList) { index, item ->
                            when(item) {
                                is MovieItem -> {
                                    MovieList.MovieItem(
                                        item = item,
                                        onItemClick = {

                                        }
                                    )
                                }
                                is AdsGroupItem -> {
                                    MovieList.AdsGroup(adsGroupItem = item)
                                }
                            }

                            if (index == movieList.lastIndex) {
                                movieListViewModel.getMovies()
                                if (!state.value.isLoadingMoreEnd) {
                                    MovieList.LoadingItem()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}