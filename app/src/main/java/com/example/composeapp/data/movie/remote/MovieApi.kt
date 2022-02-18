package com.example.composeapp.data.movie.remote

import com.example.composeapp.data.movie.remote.model.MovieDto
import com.example.composeapp.data.movie.remote.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int) : Response<MovieDto>

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int) : Response<MoviesResponse>

}