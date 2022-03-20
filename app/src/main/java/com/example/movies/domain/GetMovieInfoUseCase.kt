package com.example.movies.domain

import androidx.lifecycle.LiveData

class GetMovieInfoUseCase(private val repository: MovieRepository) {

    operator fun invoke(id: Int): LiveData<Movie> {
        return repository.getMovieById(id)
    }
}