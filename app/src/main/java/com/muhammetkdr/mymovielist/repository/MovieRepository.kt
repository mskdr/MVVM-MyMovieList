package com.muhammetkdr.mymovielist.repository

import com.muhammetkdr.mymovielist.api.RetrofitInstance

class MovieRepository {

    suspend fun getTopRatedMovies() = RetrofitInstance.api.getTopRatedMovies()

    suspend fun getPopularMovies() = RetrofitInstance.api.getPopularMovies()
}