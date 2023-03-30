package com.muhammetkdr.mymovielist.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammetkdr.mymovielist.models.Movies
import com.muhammetkdr.mymovielist.repository.MovieRepository
import com.muhammetkdr.mymovielist.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchMoviesViewModel(val moviesRepository: MovieRepository) :ViewModel() {

    private val _searchedMovies : MutableLiveData<Resource<Movies>> = MutableLiveData()
    val searchedMovies : LiveData<Resource<Movies>> get() = _searchedMovies
    private var searchMoviesResponse: Movies? = null


    fun searchMovies(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        _searchedMovies.postValue(Resource.Loading())
        val response = moviesRepository.searchMovies(searchQuery)
        _searchedMovies.postValue(handleSearchNewsResponse(response))
    }

    private fun handleSearchNewsResponse(response: Response<Movies>): Resource<Movies>? {
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                if(searchMoviesResponse == null){
                    searchMoviesResponse = resultResponse
                }else{
                    val oldMovies = searchMoviesResponse?.moviesList
                    val newsMovies = resultResponse.moviesList
                    oldMovies?.addAll(newsMovies)
                }
                return Resource.Success(searchMoviesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }





}
