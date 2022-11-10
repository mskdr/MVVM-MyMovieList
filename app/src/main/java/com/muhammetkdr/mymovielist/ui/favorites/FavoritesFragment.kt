package com.muhammetkdr.mymovielist.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.muhammetkdr.mymovielist.R
import com.muhammetkdr.mymovielist.databinding.FragmentDetailsBinding
import com.muhammetkdr.mymovielist.databinding.FragmentFavoritesBinding
import com.muhammetkdr.mymovielist.repository.MovieRepository
import com.muhammetkdr.mymovielist.roomdb.MoviesDatabase
import com.muhammetkdr.mymovielist.ui.details.DetailsViewModel

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moviesRepository = MovieRepository(MoviesDatabase(requireContext()))
        val favoritesViewModelFactory = FavoritesViewModelFactory(moviesRepository)
        favoritesViewModel = ViewModelProvider(this,favoritesViewModelFactory)[FavoritesViewModel::class.java]
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}