package com.example.movies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.TrailerItemBinding
import com.example.movies.domain.Videos

class VideosAdapter: ListAdapter<Videos, VideosAdapter.VideosViewHolder>(VideosDiffUtilCallback()) {

    var onItemClickListener: ((Videos) -> (Unit))? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val binding = TrailerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VideosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        val video = getItem(position)
        with(holder.binding) {
            textViewNameOfVideo.text = video.name
            root.setOnClickListener {
                onItemClickListener?.invoke(video)
            }
        }

    }

    class VideosViewHolder(val binding: TrailerItemBinding): RecyclerView.ViewHolder(binding.root)
}