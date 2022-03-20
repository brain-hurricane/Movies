package com.example.movies.data.mappers

import com.example.movies.data.database.MovieDbModel
import com.example.movies.data.network.model.MovieDto
import com.example.movies.domain.Movie

private const val SMALL_IMAGE_URL = "https://image.tmdb.org/t/p/w342"
private const val IMAGE_URL = "https://image.tmdb.org/t/p/w780"

object MovieMapper {

    fun mapDtoToDbModel(movieDto: MovieDto): MovieDbModel {
        return MovieDbModel(
            id = movieDto.id,
            adult = movieDto.adult,
            backdropPath = movieDto.backdropPath,
            originalLanguage = movieDto.originalLanguage,
            originalTitle = movieDto.originalTitle,
            overview = movieDto.overview,
            popularity = movieDto.popularity,
            posterPath = movieDto.posterPath,
            releaseDate = movieDto.releaseDate,
            title = movieDto.title,
            video = movieDto.video,
            voteAverage = movieDto.voteAverage,
            voteCount = movieDto.voteCount
        )
    }

    fun mapDbModelToEntity(movieDbModel: MovieDbModel): Movie = Movie(
        id = movieDbModel.id,
        adult = movieDbModel.adult,
        backdropPath = movieDbModel.backdropPath,
        originalLanguage = movieDbModel.originalLanguage,
        originalTitle = movieDbModel.originalTitle,
        overview = movieDbModel.overview,
        popularity = movieDbModel.popularity,
        posterPath = "$IMAGE_URL${movieDbModel.posterPath}",
        smallPosterPath = "$SMALL_IMAGE_URL${movieDbModel.posterPath}",
        releaseDate = movieDbModel.releaseDate,
        title = movieDbModel.title,
        video = movieDbModel.video,
        voteAverage = movieDbModel.voteAverage,
        voteCount = movieDbModel.voteCount
    )

    fun mapEntityToDbModel(movie: Movie): MovieDbModel = MovieDbModel(
        id = movie.id,
        adult = movie.adult,
        backdropPath = movie.backdropPath,
        originalLanguage = movie.originalLanguage,
        originalTitle = movie.originalTitle,
        overview = movie.overview,
        popularity = movie.popularity,
        posterPath = movie.posterPath,
        smallPosterPath = movie.smallPosterPath,
        releaseDate = movie.releaseDate,
        title = movie.title,
        video = movie.video,
        voteAverage = movie.voteAverage,
        voteCount = movie.voteCount
    )
}