package com.example.movies.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movies.data.repository.MovieRepositoryImpl
import com.example.movies.domain.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MovieRepositoryImpl(application)

    private val getMoviesUseCase = GetMoviesUseCase(repository)
    private val getMovieInfoUseCase = GetMovieInfoUseCase(repository)
    private val getVideosUseCase = GetVideosUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    var moviesList = getMoviesUseCase(MovieRepositoryImpl.SORT_BY_POPULARITY)

    private val _videos = MutableLiveData<List<Videos>>()
    val videos: LiveData<List<Videos>>
        get() = _videos

    fun getMovieInfo(id: Int): LiveData<Movie> {
        return getMovieInfoUseCase(id)
    }

    fun changeSortType(sortBy: Int) {
        moviesList = getMoviesUseCase(sortBy)
    }

    fun loadData() {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }

    fun getVideos(id: Int) {
        viewModelScope.launch {
            _videos.postValue(getVideosUseCase(id))
        }
    }

    fun getFavouritesMovies(): LiveData<List<Movie>> {
        return repository.getFavouriteMovies()
    }
}