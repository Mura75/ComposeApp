package com.example.composeapp.presentation.movie_list.view

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberImagePainter
import coil.load
import com.example.composeapp.R
import com.example.composeapp.domain.model.Movie
import com.example.composeapp.presentation.movie_list.model.AdsGroupItem
import com.example.composeapp.presentation.movie_list.model.MovieItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

object MovieList {

    @Composable
    fun MovieItem(
        item: MovieItem,
        onItemClick: (Movie) -> Unit
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp,
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    end = 8.dp
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(size = 8.dp))
                    .clickable { onItemClick(item.movie) }
                    .padding(all = 4.dp),
            ) {
                Image(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(100.dp)
                    ,
                    painter = rememberImagePainter(
                        data = item.movie.posterPath,
                        builder = {
                            placeholder(R.drawable.ic_placeholder)
                            error(R.drawable.ic_placeholder)
                        }
                    ),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier.padding(end = 8.dp),
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "${item.movie.originalTitle}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    RatingItem(rating = item.movie.voteAverage?.toFloat() ?: 0f)

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp
                            ),
                        text = item.movie.overview.orEmpty(),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

    }

    @Composable
    fun PopularMovieItem(
        item: MovieItem,
        onItemClick: (Movie) -> Unit
    ) {
        Card(
            modifier = Modifier
                .size(
                    width = 120.dp,
                    height = 180.dp
                ),
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(size = 8.dp))
                    .clickable { onItemClick(item.movie) }
            ) {
                Image(
                    modifier = Modifier
                        .size(
                            width = 120.dp,
                            height = 120.dp
                        )
                        .padding(
                            top = 8.dp
                        ),
                    painter = rememberImagePainter(
                        data = item.movie.posterPath,
                        builder = {
                            placeholder(R.drawable.ic_placeholder)
                            error(R.drawable.ic_placeholder)
                        }
                    ),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            start = 4.dp,
                            end = 4.dp
                        ),
                    text = "${item.movie.originalTitle}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Text(
                    modifier = Modifier.padding(
                        top = 8.dp,
                        start = 4.dp,
                        end = 4.dp
                    ),
                    text = "Rating: ${item.movie.voteAverage}/10",
                    maxLines = 1,
                    fontSize = 12.sp
                )
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun AdsGroup(adsGroupItem: AdsGroupItem) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                vertical = 8.dp,
                horizontal = 8.dp
            )
        ) {
            itemsIndexed(items = adsGroupItem.ads) { index, item ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = 4.dp,
                    modifier = Modifier
                ) {
                    Image(
                        modifier = Modifier
                            .size(
                                width = 256.dp,
                                height = 144.dp
                            ),
                        painter = rememberImagePainter(
                            data = item.image,
                            builder = {
                                placeholder(R.drawable.ic_placeholder)
                                error(R.drawable.ic_placeholder)
                            }
                        ),
                        contentDescription = null
                    )
                }
            }
        }
    }

    @Composable
    fun RatingItem(rating: Float) {
        Row(
            modifier = Modifier.padding(top = 8.dp),
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

    @Composable
    fun LoadingItem() {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            color = colorResource(id = R.color.purple_500)
        )
    }

    @Composable
    fun LegacyMovieItem(
        item: MovieItem,
        onItemClick: (Movie) -> Unit
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = {
               val view = LayoutInflater.from(it)
                   .inflate(R.layout.movie_item, null, false)
                val title = view.findViewById<TextView>(R.id.tvTitle)
                val description = view.findViewById<TextView>(R.id.tvDesc)
                val image = view.findViewById<ImageView>(R.id.imageView)

                title.text = item.movie.title
                description.text = item.movie.overview
                image.load(item.movie.posterPath)

                view.setOnClickListener {
                    onItemClick.invoke(item.movie)
                }

                view
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PopularMovieItem() {
    MovieList.PopularMovieItem(
        item = MovieItem(
            movie = Movie(
                id = 1,
                originalTitle = "Title",
                voteAverage = 7.5,
                overview = "Description"
            )
        ),
        onItemClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    MovieList.MovieItem(
        item = MovieItem(
            movie = Movie(
                id = 1,
                originalTitle = "Title",
                voteAverage = 7.5,
                overview = "Description"
            )
        ),
        onItemClick = {}
    )
}


@Preview(showBackground = true)
@Composable
private fun LoadingItemPreview() {
    MovieList.LoadingItem()
}

@Preview(showBackground = true)
@Composable
private fun LegacyMovieItemPreview() {
    MovieList.LegacyMovieItem(
        item = MovieItem(
            movie = Movie(
                id = 1,
                originalTitle = "Title",
                voteAverage = 7.5,
                overview = "Description"
            )
        ),
        onItemClick = {}
    )
}