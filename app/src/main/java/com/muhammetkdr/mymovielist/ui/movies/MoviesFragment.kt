package com.muhammetkdr.mymovielist.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.muhammetkdr.mymovielist.R
import com.muhammetkdr.mymovielist.adapter.MoviesAdapter
import com.muhammetkdr.mymovielist.databinding.FragmentMoviesBinding
import com.muhammetkdr.mymovielist.repository.MovieRepository
import com.muhammetkdr.mymovielist.roomdb.MoviesDatabase
import com.muhammetkdr.mymovielist.utils.Resource

class MoviesFragment : Fragment(R.layout.fragment_movies) {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        val moviesRepository = MovieRepository(MoviesDatabase(requireContext()))
        val moviesViewModelFactory = MoviesViewModelFactory(moviesRepository)
        moviesViewModel = ViewModelProvider(this, moviesViewModelFactory)[MoviesViewModel::class.java]

        moviesAdapter.setOnItemClickListener {  movieList->
            val action = MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(movieList)
            Navigation.findNavController(view).navigate(action)
        }

        moviesViewModel.popularMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { movies ->
                        moviesAdapter.differ.submitList(movies.moviesList)
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let{ message ->
                        Toast.makeText(activity,"An error occured : $message", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
//        moviesViewModel.setLoadingDataFalse()
//        isLoading = false
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
//        moviesViewModel.setLoadingDataTrue()
//        isLoading = true
    }

    private fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter()
        binding.rvMovies.apply {
            adapter = moviesAdapter
            layoutManager = GridLayoutManager(activity,2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}