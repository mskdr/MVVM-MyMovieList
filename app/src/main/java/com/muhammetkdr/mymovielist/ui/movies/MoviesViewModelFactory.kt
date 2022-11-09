package com.muhammetkdr.mymovielist.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muhammetkdr.mymovielist.repository.MovieRepository

class MoviesViewModelFactory(private val moviesRepository: MovieRepository) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MoviesViewModel::class.java)){
            return MoviesViewModel(moviesRepository) as T
        }else{
            throw IllegalStateException("Can not create instance of MoviesViewModel")
        }
    }
}