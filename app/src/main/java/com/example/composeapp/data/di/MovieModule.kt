package com.example.composeapp.data.di

import com.example.composeapp.data.movie.MovieRepositoryImpl
import com.example.composeapp.data.movie.remote.MovieApi
import com.example.composeapp.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Singleton
    @Provides
    fun provideRepository(api: MovieApi) : MovieRepository = MovieRepositoryImpl(api)
}