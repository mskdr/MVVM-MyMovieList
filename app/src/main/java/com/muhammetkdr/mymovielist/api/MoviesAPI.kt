package com.muhammetkdr.mymovielist.api

import com.muhammetkdr.mymovielist.models.Movies
import com.muhammetkdr.mymovielist.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    @GET("popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<Movies>

    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<Movies>
}
//    @GET("top_rates")
//    suspend fun searchForNews(
//        @Query("q")
//        searchQuery : String ,
//        @Query("page")
//        pageNumber : Int = 1,
//        @Query("apiKey")
//        apiKey: String = API_KEY
//    ): Response<Movies>

//https://api.themoviedb.org/3/movie/popular?api_key=d1f85d110b688bdc7c34ee9d5926f793