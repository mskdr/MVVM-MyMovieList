package com.muhammetkdr.mymovielist.repository

import com.muhammetkdr.mymovielist.api.RetrofitInstance
import com.muhammetkdr.mymovielist.models.MoviesResponse
import com.muhammetkdr.mymovielist.roomdb.MoviesDatabase

class MovieRepository(val db: MoviesDatabase) {

    suspend fun getTopRatedMovies() = RetrofitInstance.api.getTopRatedMovies()

    suspend fun getPopularMovies() = RetrofitInstance.api.getPopularMovies()

    suspend fun upsert(movies: MoviesResponse) = db.getMoviesDao().upsert(movies)

    fun getSavedMovies() = db.getMoviesDao().getAllMovies()

    suspend fun deleteArticle(movie: MoviesResponse) = db.getMoviesDao().deleteMovie(movie)
}