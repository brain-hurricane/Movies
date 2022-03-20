package com.example.movies.domain

class LoadDataUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(page: Int) {
        repository.loadData(page)
    }
}