package com.muhammetkdr.mymovielist.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muhammetkdr.mymovielist.repository.MovieRepository

class DetailsViewModelFactory(private val moviesRepository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailsViewModel::class.java)){
            return DetailsViewModel(moviesRepository) as T
        }else{
            throw IllegalStateException("Can not create instance of detailsViewModel")
        }
    }
}