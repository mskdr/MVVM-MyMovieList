package com.muhammetkdr.mymovielist.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammetkdr.mymovielist.models.MoviesResponse
import com.muhammetkdr.mymovielist.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(val moviesRepository: MovieRepository): ViewModel() {

    fun getFavoritesMovies() = moviesRepository.getSavedMovies()

    fun deleteMovie(movie: MoviesResponse) = viewModelScope.launch(Dispatchers.IO) {
        moviesRepository.deleteArticle(movie)
    }

    fun saveMovie(movie:MoviesResponse) = viewModelScope.launch(Dispatchers.IO) {
        moviesRepository.upsert(movie)
    }

}