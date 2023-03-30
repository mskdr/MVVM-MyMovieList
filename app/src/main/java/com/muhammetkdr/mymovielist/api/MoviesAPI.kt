package com.muhammetkdr.mymovielist.api

import com.muhammetkdr.mymovielist.models.Movies
import com.muhammetkdr.mymovielist.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<Movies>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("query")
        searchQuery : String,
    ): Response<Movies>
}

//https://api.themoviedb.org/3/movie/popular?api_key=d1f85d110b688bdc7c34ee9d5926f793