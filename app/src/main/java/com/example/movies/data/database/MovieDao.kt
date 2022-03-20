package com.example.movies.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies ORDER BY voteAverage DESC")
    fun getMoviesListSortAverageVote(): LiveData<List<MovieDbModel>>

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun getMoviesListSortPopularity(): LiveData<List<MovieDbModel>>

    @Query("SELECT * FROM movies WHERE id ==:id LIMIT 1")
    fun getMovieById(id: Int): LiveData<MovieDbModel>

    @Query("SELECT * FROM movies WHERE id IN (:ids)")
    fun getFavouriteMovies(ids: List<Int>): LiveData<List<MovieDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesList(listDto: List<MovieDbModel>)
}