package com.example.movies.presentation

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movies.data.repository.MovieRepositoryImpl
import com.example.movies.domain.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var currentPage = 1

    private val repository = MovieRepositoryImpl(application)

    private val getMoviesUseCase = GetMoviesUseCase(repository)
    private val getMovieInfoUseCase = GetMovieInfoUseCase(repository)
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
        if (isInternetAvailable(getApplication())) {
            viewModelScope.launch {
                loadDataUseCase(currentPage++)
            }
        }
    }

    fun getVideos(id: Int) {
        if (isInternetAvailable(getApplication())) {
            viewModelScope.launch {
                _videos.postValue(repository.getVideos(id))
            }
        }
    }

    fun getFavouritesMovies(): LiveData<List<Movie>> {
        return repository.getFavouriteMovies()
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val result: Boolean
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TEST", "onCleared")
    }
}