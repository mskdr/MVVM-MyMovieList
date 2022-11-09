package com.muhammetkdr.mymovielist.models

import com.google.gson.annotations.SerializedName

data class Movies(
    val page: Int,
    @SerializedName("results")
    val moviesList: MutableList<MoviesResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)