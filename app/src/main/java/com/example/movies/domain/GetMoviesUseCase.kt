package com.example.movies.domain

import androidx.lifecycle.LiveData

class GetMoviesUseCase(private val repository: MovieRepository) {

    operator fun invoke(sortBy: Int): LiveData<List<Movie>> {
        return repository.getMovies(sortBy)
    }
}