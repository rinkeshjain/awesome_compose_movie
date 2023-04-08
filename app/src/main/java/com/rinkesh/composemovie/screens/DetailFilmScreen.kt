package com.rinkesh.composemovie.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.palette.graphics.Palette
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rinkesh.composemovie.R
import com.rinkesh.composemovie.data.local.MyListMovie
import com.rinkesh.composemovie.model.Film
import com.rinkesh.composemovie.screens.commonview.*
import com.rinkesh.composemovie.screens.destinations.ReviewScreenDestination
import com.rinkesh.composemovie.screens.destinations.WatchOttScreenDestination
import com.rinkesh.composemovie.ui.theme.AppOnPrimaryColor
import com.rinkesh.composemovie.ui.theme.AppPrimaryColor
import com.rinkesh.composemovie.ui.theme.TranslucentAppPrimaryColor
import com.rinkesh.composemovie.utlis.FilmType
import com.rinkesh.composemovie.utlis.Utils.BASE_BACKDROP_IMAGE_URL
import com.rinkesh.composemovie.utlis.Utils.BASE_POSTER_IMAGE_URL
import com.rinkesh.composemovie.viewmodels.MovieDetailViewModel
import com.rinkesh.composemovie.viewmodels.WatchListViewModel
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.coil.CoilImageState
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import java.text.SimpleDateFormat
import java.util.*

@Destination
@Composable
fun DetailFilmScreen(
    currentFilm: Film,
    currentFilmType: FilmType,
    watchListViewModel: WatchListViewModel = hiltViewModel(),
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    var startColor by remember{ mutableStateOf(Color(0xFFA3A0AD)) }
    var endColor by remember{ mutableStateOf(Color(0xFF000000)) }
    var film by remember { mutableStateOf(currentFilm) }
    val filmType by remember { mutableStateOf(currentFilmType) }
    val date = SimpleDateFormat.getDateInstance().format(Date())
    val myListMovie = MyListMovie(
        mediaId = film.id,
        imagePath = film.posterPath,
        title = film.title,
        releaseDate = film.releaseDate,
        rating = film.voteAverage,
        addedOn = date
    )
    LaunchedEffect(key1 = film) {
        watchListViewModel.exist(film.id)
        movieDetailViewModel.getSimilarMovie(filmId = film.id, filmType)
        movieDetailViewModel.getFilmTag(filmType = filmType)
        movieDetailViewModel.getFilmCast(filmType = filmType, medialId = film.id)
        movieDetailViewModel.watchOttFun(filmType = filmType, mediaId = film.id)
    }
    val context = LocalContext.current
    val cast = movieDetailViewModel.filmCast
    val similarMovie = movieDetailViewModel.similarMovie.value.collectAsLazyPagingItems()

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
        ) {
            val (backButton, backDropImage, translucentBox, moviePosterImage, movieDetailBox) = createRefs()
            CoilImage(
                        imageModel ={ "$BASE_BACKDROP_IMAGE_URL${film.backdropPath}"},
                onImageStateChanged = {imageState->
                    if(imageState is CoilImageState.Success) {
                        val palate = Palette.from(imageState.imageBitmap!!.asAndroidBitmap().copy(Bitmap.Config.ARGB_8888, false)).generate()
                        endColor = Color(palate.getLightVibrantColor(0x000000))
                        println("startColor")
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(backDropImage) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                component = rememberImageComponent {
                    +CrossfadePlugin(duration = 350)
                    +ShimmerPlugin(
                        tilt = 20f,
                        dropOff = 0.65f,
                        durationMillis = 500,
                        baseColor = AppPrimaryColor,
                        highlightColor = AppOnPrimaryColor
                    )
                },
                failure = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.image_not_available),
                            contentDescription = "Failed to load"
                        )
                    }
                },
            )
            BackButton(modifier = Modifier.constrainAs(backButton) {
                start.linkTo(backDropImage.start, margin = 16.dp)
                top.linkTo(backDropImage.top, margin = 16.dp)
            }.background(endColor), onClick = { navigator.navigateUp() })
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                startColor,
                                endColor
                            ),
                            startY = 0.1F
                        )
                    )
                    .constrainAs(translucentBox) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
            PosterCoilImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .width(115.dp)
                    .height(172.5.dp)
                    .constrainAs(moviePosterImage) {
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(movieDetailBox.start)
                        bottom.linkTo(parent.bottom)
                    },
                url = "$BASE_POSTER_IMAGE_URL/${film.posterPath}"
            )
            Column(
                modifier = Modifier
                    .constrainAs(movieDetailBox) {
                        start.linkTo(moviePosterImage.end, margin = 16.dp)
                        end.linkTo(parent.end, margin = 12.dp)
                        bottom.linkTo(parent.bottom)
                    },
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(TranslucentAppPrimaryColor)
                            .clip(MaterialTheme.shapes.large),
                    ) {
                        Text(
                            text = when (filmType) {
                                FilmType.MOVIE -> "Movie"
                                FilmType.TVSHOW -> "Series"
                            },
                            color = Color.Green,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(TranslucentAppPrimaryColor)
                            .clip(MaterialTheme.shapes.large),
                    ) {
                        Text(
                            text = when (film.adult) {
                                true -> "+18"
                                false -> "PG"
                            },
                            color = when (film.adult) {
                                true -> Color.Red
                                false -> Color.Green
                            },
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
                Text(
                    text = film.title,
                    modifier = Modifier.fillMaxWidth(.55f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = film.releaseDate,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelSmall
                )
                RatingBarUI(vote = film.voteAverage)
                IconRow(
                    exist = watchListViewModel.exist.value,
                    context = context,
                    removeOnClick = { watchListViewModel.removeFromWatchList(mediaId = film.id) },
                    addOnClick = { watchListViewModel.addToWatchList(myListMovie) },
                    onReviewClick = {
                        navigator.navigate(
                            direction = ReviewScreenDestination(
                                filmType = filmType,
                                mediaId = film.id,
                                filmTitle = film.title
                            )
                        )
                    },
                    ottOnClick = {
                        navigator.navigate(
                            direction = WatchOttScreenDestination(
                                filmType = filmType,
                                mediaId = film.id,
                                title = film.title
                            )
                        )
                    }
                )
            }
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
        ) {
            val genreList = movieDetailViewModel.movieGenreState.filter { genre ->
                return@filter if (film.genreIds.isNullOrEmpty()) {
                    false
                } else {
                    film.genreIds!!.contains(genre.id)
                }
            }
            items(genreList) {
                DetailGenreChip(title = it.name)
            }
        }
        ExpandableText(text = film.overview, modifier = Modifier.padding(16.dp))
        LazyColumn() {
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(cast.value) {
                        CircleCoilImage(
                            url = "$BASE_POSTER_IMAGE_URL/${it.profilePath}",
                            name = it.name,
                            job = it.department
                        )
                    }
                }
            }
            item {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(similarMovie) { thisMovie ->
                        PosterCoilImage(
                            modifier = Modifier
                                .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .size(130.dp, 195.dp)
                                .clickable {
                                    film = thisMovie!!
                                },
                            url = "${BASE_POSTER_IMAGE_URL}/${thisMovie!!.posterPath}"
                        )
                    }
                }
            }
        }
    }
}
