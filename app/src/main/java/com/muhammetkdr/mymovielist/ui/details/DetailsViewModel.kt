package com.muhammetkdr.mymovielist.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammetkdr.mymovielist.models.MoviesResponse
import com.muhammetkdr.mymovielist.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(val moviesRepository: MovieRepository) : ViewModel() {

    fun saveMovie(movie:MoviesResponse) = viewModelScope.launch(Dispatchers.IO) {
        moviesRepository.upsert(movie)
    }

    init {
        getCurrentMovieList()
    }

    fun getCurrentMovieList() = moviesRepository.getSavedMovies()

}