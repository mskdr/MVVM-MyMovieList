package com.muhammetkdr.mymovielist.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.muhammetkdr.mymovielist.R
import com.muhammetkdr.mymovielist.adapter.MoviesAdapter
import com.muhammetkdr.mymovielist.databinding.FragmentFavoritesBinding
import com.muhammetkdr.mymovielist.repository.MovieRepository
import com.muhammetkdr.mymovielist.roomdb.MoviesDatabase

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var favoritesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewAdapter()

        val moviesRepository = MovieRepository(MoviesDatabase(requireContext()))
        val favoritesViewModelFactory = FavoritesViewModelFactory(moviesRepository)
        favoritesViewModel =
            ViewModelProvider(this, favoritesViewModelFactory)[FavoritesViewModel::class.java]

        favoritesAdapter.setOnItemClickListener { movie ->
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(movie)
            Navigation.findNavController(view).navigate(action)
        }

        favoritesViewModel.getFavoritesMovies().observe(viewLifecycleOwner) { moviesList ->
            favoritesAdapter.differ.submitList(moviesList)
        }

        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val movie = favoritesAdapter.differ.currentList[position]
                favoritesViewModel.deleteMovie(movie)
                Snackbar.make(view, "${movie.title} deleted successfully!", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo") {
                            favoritesViewModel.saveMovie(movie)
                        }
                        show()
                    }
            }
        }
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvFavorites)
        }
    }

    private fun setupRecyclerViewAdapter() {
        favoritesAdapter = MoviesAdapter()
        binding.rvFavorites.apply {
            adapter = favoritesAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}