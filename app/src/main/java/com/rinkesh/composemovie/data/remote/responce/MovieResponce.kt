package com.rinkesh.composemovie.data.remote.responce


import com.google.gson.annotations.SerializedName
import com.rinkesh.composemovie.model.Film

data class MovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Film>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
