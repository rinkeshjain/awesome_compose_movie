package com.rinkesh.composemovie.data.remote.responce


import com.google.gson.annotations.SerializedName
import com.rinkesh.composemovie.model.Genre

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)