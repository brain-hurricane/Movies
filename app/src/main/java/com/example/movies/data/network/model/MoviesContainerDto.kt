package com.example.movies.data.network.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MoviesContainerDto {
    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("results")
    @Expose
    var results: List<MovieDto>? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null

}