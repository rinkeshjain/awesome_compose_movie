package com.rinkesh.composemovie.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rinkesh.composemovie.data.remote.responce.Review
import com.rinkesh.composemovie.screens.commonview.BackButton
import com.rinkesh.composemovie.screens.commonview.CircleCoilImage
import com.rinkesh.composemovie.screens.commonview.RatingBarUI
import com.rinkesh.composemovie.ui.theme.ButtonColor
import com.rinkesh.composemovie.utlis.FilmType
import com.rinkesh.composemovie.utlis.Utils.BASE_POSTER_IMAGE_URL
import com.rinkesh.composemovie.viewmodels.ReviewViewModel

@Destination
@Composable
fun ReviewScreen(
    filmType: FilmType,
    mediaId: Int,
    filmTitle: String,
    navigator: DestinationsNavigator,
    reviewViewModel: ReviewViewModel = hiltViewModel()
) {
    val reviewData = reviewViewModel.filmReviews.value.collectAsLazyPagingItems()
    LaunchedEffect(key1 = mediaId) {
        reviewViewModel.getReviewData(filmType, mediaId)
    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                BackButton(modifier = Modifier) {
                    navigator.navigateUp()
                }
                Text(
                    text = filmTitle,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
        items(reviewData) {
            ReviewPost(item = it)
        }
    }
}

@Composable
fun ReviewPost(item: Review?) {
    Column() {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                CircleCoilImage(url = "${BASE_POSTER_IMAGE_URL}${item?.authorDetails?.avatarPath}")
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item?.authorDetails?.name ?: "Anonymous",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1
                )
                Text(
                    text = item?.authorDetails?.userName ?: "Anonymous",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    color = Color.Gray
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp, start = 8.dp, end = 8.dp)
                .background(ButtonColor)
                .clip(MaterialTheme.shapes.large),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = item?.content ?: "Not Review Found",
                    style = MaterialTheme.typography.bodyLarge
                )
                RatingBarUI(vote = (item?.authorDetails?.rating ?: 0).toDouble())
                Text(text = item?.createdOn?.removeRange(10..item.createdOn.lastIndex) ?: "")
            }
        }
    }
}