package com.muhammetkdr.mymovielist.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muhammetkdr.mymovielist.repository.MovieRepository

class SearchMoviesViewModelFactory(private val moviesRepository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchMoviesViewModel::class.java)){
            return SearchMoviesViewModel(moviesRepository) as T
        }else{
            throw IllegalStateException("Can not create instance of searchMoviesViewModel")
        }
    }
}