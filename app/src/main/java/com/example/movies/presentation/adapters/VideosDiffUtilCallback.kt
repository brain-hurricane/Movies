package com.example.movies.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.movies.domain.Movie
import com.example.movies.domain.Videos

class VideosDiffUtilCallback : DiffUtil.ItemCallback<Videos>() {
    override fun areItemsTheSame(oldItem: Videos, newItem: Videos): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Videos, newItem: Videos): Boolean {
        return oldItem == newItem
    }
}