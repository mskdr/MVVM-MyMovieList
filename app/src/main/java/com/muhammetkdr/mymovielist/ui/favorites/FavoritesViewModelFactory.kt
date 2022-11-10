package com.muhammetkdr.mymovielist.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muhammetkdr.mymovielist.repository.MovieRepository

class FavoritesViewModelFactory(private val moviesRepository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoritesViewModel::class.java)){
            return FavoritesViewModel(moviesRepository) as T
        }else{
            throw IllegalStateException("Can not create instance of MoviesViewModel")
        }
    }
}