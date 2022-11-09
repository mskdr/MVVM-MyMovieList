package com.muhammetkdr.mymovielist.ui.movies

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

class MoviesViewModel(val moviesRepository: MovieRepository) : ViewModel() {

    private val _popularMovies : MutableLiveData<Resource<Movies>> = MutableLiveData()
    val popularMovies : LiveData<Resource<Movies>> get() = _popularMovies
    private var popularMoviesResponse: Movies? = null

    init {
        getPopularMovies()
    }

    fun getPopularMovies() = viewModelScope.launch(Dispatchers.IO) {
        _popularMovies.postValue(Resource.Loading())
        val response = moviesRepository.getPopularMovies()
        _popularMovies.postValue(handlepopularMoviesResponse(response))
    }

    private fun handlepopularMoviesResponse(response: Response<Movies>) : Resource<Movies>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                if(popularMoviesResponse == null){
                    popularMoviesResponse = resultResponse
                }else{
                    val oldMovies = popularMoviesResponse?.moviesList
                    val newsArticles = resultResponse.moviesList
                    oldMovies?.addAll(newsArticles)
                }
                return Resource.Success(popularMoviesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}