package com.example.movies.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.movies.data.database.AppDatabase
import com.example.movies.data.mappers.MovieMapper
import com.example.movies.data.mappers.VideosMapper
import com.example.movies.data.network.ApiFactory
import com.example.movies.data.network.ApiService
import com.example.movies.data.network.URL_GET_VIDEOS
import com.example.movies.data.preferences.AppSettings
import com.example.movies.domain.Movie
import com.example.movies.domain.MovieRepository
import com.example.movies.domain.Videos


class MovieRepositoryImpl(private val application: Application) : MovieRepository {

    private val dao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val apiService = ApiFactory.apiService

    override fun getMovies(sortBy: Int): LiveData<List<Movie>> {
        val listMoviesDbModel = if (sortBy == SORT_BY_POPULARITY) {
            dao.getMoviesListSortPopularity()
        } else if (sortBy == SORT_BY_AVERAGE_VOTE) {
            dao.getMoviesListSortAverageVote()
        } else {
            throw RuntimeException("value sortBy not found. Use SORT_BY_POPULARITY or SORT_BY_AVERAGE_VOTE")
        }
        return Transformations.map(listMoviesDbModel) {
            it.map {
                MovieMapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getMovieById(id: Int): LiveData<Movie> {
        return Transformations.map(dao.getMovieById(id)) {
            MovieMapper.mapDbModelToEntity(it)
        }
    }

    override fun getFavouriteMovies(): LiveData<List<Movie>> {
        val idsOfFavouriteMovies = AppSettings(application).getAllFavouriteIds()
        return Transformations.map(dao.getFavouriteMovies(idsOfFavouriteMovies)) {
            it.map {
                MovieMapper.mapDbModelToEntity(it)
            }
        }
    }

    override suspend fun loadData(page: Int) {
        try {
            val moviesDto = apiService.getMovies(page = page.toString())
            val moviesDbModel = moviesDto.results?.map {
                MovieMapper.mapDtoToDbModel(it)
            } ?: throw RuntimeException("Error in loadData(), moviesDbModel == null, line 30 MovieRepositoryImpl")
            dao.insertMoviesList(moviesDbModel)
        } catch (e: Exception) {
        }
    }

    override suspend fun getVideos(id: Int): List<Videos>? {
        return try {
            val url = String.format(URL_GET_VIDEOS, id.toString())
            val videosDto = apiService.getVideos(url)
            videosDto.results?.map {
                VideosMapper.mapDtoToEntity(it)
            }
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        const val SORT_BY_POPULARITY = 0
        const val SORT_BY_AVERAGE_VOTE = 1
    }
}