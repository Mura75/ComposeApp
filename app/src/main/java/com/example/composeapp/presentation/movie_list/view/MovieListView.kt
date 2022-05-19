package com.example.composeapp.presentation.movie_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composeapp.presentation.Screen
import com.example.composeapp.presentation.movie_list.model.AdsGroupItem
import com.example.composeapp.presentation.movie_list.model.MovieItem
import com.example.composeapp.presentation.movie_list.view.MovieList
import com.example.composeapp.presentation.theme.ComposeAppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MovieListView(navController: NavController) {

    val viewModel: MovieListViewModel = hiltViewModel()

    val state = viewModel.listState.collectAsState()
    val isRefreshing = rememberSwipeRefreshState(state.value.isLoading)

    val movieList = state.value.items

    val listState = rememberLazyListState()

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
                    onRefresh = {
                        viewModel.onEvent(MovieListEvent.Refresh)
                    }
                ) {
                    LazyColumn(
                        state = listState,
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
                                                onItemClick = {
                                                    navController.navigate(
                                                        route = Screen.MovieDetail.withMovieId(
                                                            movieId = item.movie.id,
                                                            movieName = item.movie.title.orEmpty()
                                                        )
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        itemsIndexed(items = movieList) { index, item ->
                            when(item) {
                                is MovieItem -> {
                                    MovieList.LegacyMovieItem(
                                        item = item,
                                        onItemClick = {
                                            navController.navigate(
                                                route = Screen.MovieDetail.withMovieId(
                                                    movieId = item.movie.id,
                                                    movieName = item.movie.title.orEmpty()
                                                )
                                            )
                                        }
                                    )
                                }
                                is AdsGroupItem -> {
                                    MovieList.AdsGroup(adsGroupItem = item)
                                }
                            }

                            if (index == movieList.lastIndex) {
                                viewModel.onEvent(MovieListEvent.LoadMore)
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

    LaunchedEffect(listState) {
        val lastPosition = state.value.lastPosition
        val scrollOffset = state.value.scrollOffset
        if (lastPosition > 0 && scrollOffset > 0) {
            listState.scrollToItem(lastPosition, scrollOffset)
        }
    }

    DisposableEffect(listState) {
        onDispose {
            viewModel.onEvent(
                event = MovieListEvent.SaveState(
                    lastPosition = listState.firstVisibleItemIndex,
                    scrollOffset = listState.firstVisibleItemScrollOffset
                )
            )
        }
    }
}