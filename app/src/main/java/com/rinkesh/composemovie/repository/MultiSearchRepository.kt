package com.rinkesh.composemovie.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rinkesh.composemovie.data.remote.ApiService
import com.rinkesh.composemovie.model.Search
import com.rinkesh.composemovie.paging.MultiSearchSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MultiSearchRepository @Inject constructor(private val apiService: ApiService) {
    fun searchResult(searchPars: String): Flow<PagingData<Search>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = { MultiSearchSource(apiService, searchPars) }
        ).flow
    }
}