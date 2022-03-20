package com.example.movies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.MovieItemBinding
import com.example.movies.domain.Movie
import com.squareup.picasso.Picasso

class MovieAdapter: ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffUtilCallback()) {

    var onPosterClickListener: ((Movie) -> (Unit))? = null
    var onReachEndListener: (() -> (Unit))? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        Picasso.get().load(movie.smallPosterPath).into(holder.binding.poster)
        holder.binding.root.setOnClickListener {
            onPosterClickListener?.invoke(movie)
        }
        if (position > itemCount - 4) {
            onReachEndListener?.invoke()
        }
        //Log.d("TEST", position.toString())
    }

    class MovieViewHolder(val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root)
}