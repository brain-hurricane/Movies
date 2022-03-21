package com.example.movies.domain

import androidx.lifecycle.LiveData

interface MovieRepository {

    fun getMovies(sortBy: Int): LiveData<List<Movie>>

    fun getMovieById(id: Int): LiveData<Movie>

    fun getFavouriteMovies(): LiveData<List<Movie>>

    suspend fun loadData()

    suspend fun getVideos(id: Int): List<Videos>?
}