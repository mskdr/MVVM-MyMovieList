package com.muhammetkdr.mymovielist.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.muhammetkdr.mymovielist.R
import com.muhammetkdr.mymovielist.databinding.FragmentDetailsBinding
import com.muhammetkdr.mymovielist.repository.MovieRepository
import com.muhammetkdr.mymovielist.roomdb.MoviesDatabase


class DetailsFragment : Fragment(R.layout.fragment_details) {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailsViewModel: DetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater,container,false)

        val moviesRepository = MovieRepository(MoviesDatabase(requireContext()))
        val detailsViewModelFactory = DetailsViewModelFactory(moviesRepository)
        detailsViewModel = ViewModelProvider(this, detailsViewModelFactory)[DetailsViewModel::class.java]

        arguments?.let {
            val args = DetailsFragmentArgs.fromBundle(it)
            args.let {
                detailsViewModel.getCurrentMovieList().observe(viewLifecycleOwner){ movieList->
                    movieList?.let {
                        for (movie in movieList){
                            val isTheSame = movie.title.equals(args.movie.title)
                            if(isTheSame){
                                binding.fapFavButton.visibility = View.INVISIBLE
                            }
                        }
                    }
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val args = DetailsFragmentArgs.fromBundle(it)
            args.let { movieList->
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/${movieList.movie.posterPath}")
                    .into(binding.detailsImageView)
                binding.detailsTitle.text = movieList.movie.title
                binding.detailsOverView.text = movieList.movie.overview
            }
            binding.fapFavButton.setOnClickListener{
                detailsViewModel.saveMovie(args.movie)
                Snackbar.make(view,"${args.movie.title} saved succesfully!", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        detailsViewModel.deleteMovie(args.movie)
                        binding.fapFavButton.visibility = View.VISIBLE
                    }
                show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}