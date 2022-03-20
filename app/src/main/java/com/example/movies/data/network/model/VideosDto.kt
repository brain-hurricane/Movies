package com.example.movies.data.network.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VideosDto (
    @SerializedName("iso_639_1")
    @Expose
    var iso6391: String? = null,

    @SerializedName("iso_3166_1")
    @Expose
    var iso31661: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("key")
    @Expose
    var key: String? = null,

    @SerializedName("site")
    @Expose
    var site: String? = null,

    @SerializedName("size")
    @Expose
    var size: Int? = null,

    @SerializedName("type")
    @Expose
    var type: String? = null,

    @SerializedName("official")
    @Expose
    var official: Boolean? = null,

    @SerializedName("published_at")
    @Expose
    var publishedAt: String? = null,

    @SerializedName("id")
    @Expose
    var id: String? = null
)