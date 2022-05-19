package com.example.composeapp.presentation.movie_detail

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.composeapp.R
import com.example.composeapp.presentation.theme.ComposeAppTheme
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

@Composable
fun MovieDetailView(movieId: Int, name: String) {

    val viewModel: MovieDetailViewModel = hiltViewModel()

    val state = viewModel.movieState.collectAsState()
    val movie = state.value.movie

    viewModel.onEvent(MovieDetailEvent.MovieDetail(id = movieId))

    ComposeAppTheme(
        darkTheme = false
    ) {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopAppBar(
                    title = {
                        Text(text = name)
                    }
                )
                Crossfade(
                    state.value.isLoading
                ) { isLoading ->
                    if (isLoading) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator(
                                color = colorResource(id = R.color.purple_500),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(64.dp)
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .padding(top = 8.dp)
                                ,
                                painter = rememberImagePainter(
                                    data = movie?.backdropPath,
                                    builder = {
                                        placeholder(R.drawable.ic_placeholder)
                                        error(R.drawable.ic_placeholder)
                                    }
                                ),
                                contentDescription = null
                            )
                            RatingItem(
                                rating = movie?.voteAverage?.toFloat() ?: 0f
                            )

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 8.dp,
                                        bottom = 16.dp
                                    ),
                                text = "${movie?.voteCount} votes",
                                fontSize = 16.sp
                            )

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp
                                    ),
                                text = movie?.genres?.joinToString(", ") { it.name }.orEmpty(),
                                fontSize = 14.sp
                            )

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 24.dp
                                    ),
                                text = "Overview",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 16.dp,
                                        bottom = 24.dp
                                    ),
                                text = movie?.overview.orEmpty(),
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RatingItem(rating: Float) {
    Row(
        modifier = Modifier.padding(
            top = 8.dp,
            start = 16.dp,
            end = 16.dp
        ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val config = RatingBarConfig()
            .activeColor(Color.Cyan)
            .hideInactiveStars(true)
            .inactiveColor(Color.LightGray)
            .stepSize(StepSize.HALF)
            .numStars(10)
            .isIndicator(true)
            .size(16.dp)
            .padding(1.dp)
            .style(RatingBarStyle.Normal)
        RatingBar(
            value = rating,
            config = config,
            onRatingChanged = {},
            onValueChange = {}
        )

        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = "${rating}/10"
        )
    }
}