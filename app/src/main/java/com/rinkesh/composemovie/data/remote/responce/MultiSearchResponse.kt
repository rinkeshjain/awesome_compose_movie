package com.rinkesh.composemovie.data.remote.responce

import com.google.gson.annotations.SerializedName
import com.rinkesh.composemovie.model.Search


class MultiSearchResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Search>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
