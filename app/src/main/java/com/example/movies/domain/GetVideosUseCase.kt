package com.example.movies.domain

class GetVideosUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(id: Int): List<Videos>? {
        return repository.getVideos(id)
    }
}