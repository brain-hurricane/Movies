package com.example.movies.domain

data class Videos(
    var iso6391: String? = null,
    var iso31661: String? = null,
    var name: String? = null,
    var key: String? = null,
    var site: String? = null,
    var size: Int? = null,
    var type: String? = null,
    var official: Boolean? = null,
    var publishedAt: String? = null,
    var id: String? = null
)