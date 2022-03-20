package com.example.movies.data.preferences

import android.content.Context
import com.example.movies.domain.Movie

class AppSettings(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        context.packageName + "_favouriteMovies",
        Context.MODE_PRIVATE
    )
    private val editor = sharedPreferences.edit()

    fun changeFavouriteState(movie: Movie, isFavourite: Boolean) {
        editor.putBoolean(movie.id.toString(), isFavourite)
        editor.apply()
    }

    fun getFavouriteState(movie: Movie): Boolean {
        return sharedPreferences.getBoolean(movie.id.toString(), false)
    }

    fun getAllFavouriteIds(): List<Int> {
        val allValues = sharedPreferences.all
        val result = mutableListOf<Int>()
        for((key, value) in allValues){
            if (value as Boolean) {
                result.add(key.toInt())
            }
        }
        return result
    }

}