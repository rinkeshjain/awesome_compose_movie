package com.rinkesh.composemovie.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rinkesh.composemovie.data.remote.responce.Review
import com.rinkesh.composemovie.repository.ReviewRepository
import com.rinkesh.composemovie.utlis.FilmType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(private val reviewsRepository: ReviewRepository) :
    ViewModel() {
    private val _filmReviews = mutableStateOf<Flow<PagingData<Review>>>(emptyFlow())
    val filmReviews: State<Flow<PagingData<Review>>> = _filmReviews

    fun getReviewData(filmType: FilmType, mediaId: Int) {
        viewModelScope.launch {
            _filmReviews.value =
                reviewsRepository.getReviewData(mediaId, filmType).cachedIn(viewModelScope)
        }
    }
}