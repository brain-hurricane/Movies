package com.example.movies.data.network.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class VideosContainerDto (
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("results")
    @Expose
    var results: List<VideosDto>? = null
)