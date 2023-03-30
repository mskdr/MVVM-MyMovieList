package com.muhammetkdr.mymovielist.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.muhammetkdr.mymovielist.R
import com.muhammetkdr.mymovielist.adapter.MoviesAdapter
import com.muhammetkdr.mymovielist.databinding.FragmentSearchBinding
import com.muhammetkdr.mymovielist.repository.MovieRepository
import com.muhammetkdr.mymovielist.roomdb.MoviesDatabase
import com.muhammetkdr.mymovielist.utils.Constants.Companion.SEARCH_MOVIES_TIME_DELAY
import com.muhammetkdr.mymovielist.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchMoviesFragment : Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchMoviesViewModel: SearchMoviesViewModel
    private lateinit var searchAdapter : MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        val moviesRepository = MovieRepository(MoviesDatabase(requireContext()))
        val searchMoviesViewModelFactory = SearchMoviesViewModelFactory(moviesRepository)
        searchMoviesViewModel = ViewModelProvider(this, searchMoviesViewModelFactory)[SearchMoviesViewModel::class.java]


        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_MOVIES_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        searchMoviesViewModel.searchMovies(editable.toString())

                    }
                }
            }
        }

        searchAdapter.setOnItemClickListener { movie->
                val action = SearchMoviesFragmentDirections.actionSearchMoviesFragmentToDetailsFragment(movie)
                Navigation.findNavController(view).navigate(action)
        }

        searchMoviesViewModel.searchedMovies.observe(viewLifecycleOwner){ response->
            when(response){
                is Resource.Success -> {
                    showProgressBar(false)
                    response.data?.let { newsResponse ->
                        searchAdapter.differ.submitList(newsResponse.moviesList.toList())
                    }
                }
                is Resource.Error -> {
                    showProgressBar(true)
                    response.message?.let { message ->
                        Log.e(Companion.TAG,"An error occured :  $message")
                        Toast.makeText(activity,"An error occured: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar(true)
                }
            }
        }
    }
    
    private fun setupRecyclerView() {
        searchAdapter = MoviesAdapter()
        binding.rvSearchMovies.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(activity,2)
        }
    }

    fun showProgressBar(isVisible: Boolean){
        binding.paginationProgressBar.isVisible = isVisible
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "SEARCH_MOVIE_FRAGMENT"
    }
}