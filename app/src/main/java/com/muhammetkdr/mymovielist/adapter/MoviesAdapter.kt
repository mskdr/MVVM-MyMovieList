package com.muhammetkdr.mymovielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.muhammetkdr.mymovielist.databinding.MovieItemsBinding
import com.muhammetkdr.mymovielist.models.MoviesResponse

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<MoviesResponse>() {
        override fun areItemsTheSame(oldItem: MoviesResponse, newItem: MoviesResponse): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MoviesResponse, newItem: MoviesResponse): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    inner class MoviesViewHolder(val moviesBinding: MovieItemsBinding) :
        ViewHolder(moviesBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val moviesItemBinding =
            MovieItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(moviesItemBinding)
    }
    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movies = differ.currentList[position]
        movies?.let {
            holder.itemView.apply {
                Glide.with(holder.itemView.context)
                    .load("https://image.tmdb.org/t/p/w500/${movies.posterPath}")
                    .into(holder.moviesBinding.recyclerImageView)
                holder.moviesBinding.overviewTextView.text = movies.overview
                holder.moviesBinding.titleTextView.text = movies.title
                setOnClickListener {
                    onItemClickListener?.let {
                        it(movies)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((MoviesResponse) -> Unit)? = null

    fun setOnItemClickListener(listener: (MoviesResponse) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int = differ.currentList.size
}