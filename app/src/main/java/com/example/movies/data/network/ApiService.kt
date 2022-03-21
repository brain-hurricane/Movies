package com.example.movies.data.network

import com.example.movies.data.network.model.MoviesContainerDto
import com.example.movies.data.network.model.VideosContainerDto
import com.example.movies.data.network.model.VideosDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

private const val API_KEY = "750a11cbe9de6c36b3951c85861fb3b8"
private const val QUERY_PARAM_API_KEY = "api_key"
private const val QUERY_PARAM_LANG = "language"
private const val QUERY_PARAM_PAGE = "page"
private const val QUERY_VOTE_COUNT = "vote_count.gte"

const val URL_GET_VIDEOS = "movie/%s/videos"
const val BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v="

interface ApiService {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANG) lang: String = "ru",
        @Query(QUERY_PARAM_PAGE) page: String,
        @Query(QUERY_VOTE_COUNT) voteCount: String = "1000",
    ): MoviesContainerDto

    @GET
    suspend fun getVideos(
        @Url url: String,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANG) lang: String = "ru",
    ): VideosContainerDto
}