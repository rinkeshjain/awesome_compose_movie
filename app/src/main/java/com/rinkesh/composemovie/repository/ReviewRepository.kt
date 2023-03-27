package com.rinkesh.composemovie.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rinkesh.composemovie.data.remote.ApiService
import com.rinkesh.composemovie.data.remote.responce.Review
import com.rinkesh.composemovie.paging.ReviewSource
import com.rinkesh.composemovie.utlis.FilmType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewRepository @Inject constructor(private val apiService: ApiService) {
    fun getReviewData(mediaId: Int, filmType: FilmType): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { ReviewSource(apiService, filmType, mediaId) }
        ).flow
    }
}