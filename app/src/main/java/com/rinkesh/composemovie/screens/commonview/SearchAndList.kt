package com.rinkesh.composemovie.screens.commonview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rinkesh.composemovie.model.Genre
import com.rinkesh.composemovie.ui.theme.ButtonColor
import com.rinkesh.composemovie.utlis.FilmType
import com.rinkesh.composemovie.viewmodels.HomeViewModel

@Composable
fun SearchAndList(
    homeViewModel: HomeViewModel,
    onRoomWatchList: () -> Unit,
    onSearchScreen: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(64.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val filmTypeList = listOf(FilmType.MOVIE, FilmType.TVSHOW)
        IconButton(onClick = { onRoomWatchList() }) {
            Icon(imageVector = Icons.Default.MovieFilter, contentDescription = "Movie Filter")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            (filmTypeList).forEach { filmType ->
                Box(modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(if (homeViewModel.selectedFilmType.value == filmType) Color.Red else Color.White)
                    .clickable {
                        if (homeViewModel.selectedFilmType.value != filmType)
                            homeViewModel.selectedFilmType.value = filmType
                        homeViewModel.selectedGenre.value = Genre(null, "All")
                        homeViewModel.hitNetworkCall()
                    }) {
                    Text(
                        text = if (filmType == FilmType.MOVIE) "Movie" else "Tv Show",
                        color = if (homeViewModel.selectedFilmType.value == filmType) Color.White else ButtonColor,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }
            }
        }
        IconButton(onClick = { onSearchScreen() }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Movie Search")
        }
    }
}