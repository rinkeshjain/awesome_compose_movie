package com.rinkesh.composemovie.screens.commonview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rinkesh.composemovie.R
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CoilImageUI(
    modifier: Modifier,
    landscape: Boolean,
    title: String,
    imgUrl: String,
    onClick: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .clickable { onClick() }) {
        CoilImage(
            imageModel = {imgUrl},
//            component=rememberImageComponent{
//                +BlurTransformationPlugin(
//                    baseColor = background800,
//                    highlightColor = shimmerHighLight
//                )
//            },
            modifier = Modifier
                .weight(1f)
                .clip(MaterialTheme.shapes.large),


//            shimmerParams = ShimmerParams(
//                baseColor = AppPrimaryColor,
//                durationMillis = 500,
//                tilt = 20f,
//                highlightColor = Color.DarkGray
//            ),
            failure = {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.image_not_available),
                        contentDescription = "Field to load Image"
                    )
                }
            },
//            circularReveal = CircularReveal(duration = 1000),
        )
        AnimatedVisibility(visible = landscape) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}


//@Composable
//fun PosterCoilImage(modifier: Modifier, url: String) {
//    CoilImage(
//        imageModel = {url},
//        modifier = modifier,
//        failure = {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Image(
//                    painter = painterResource(id = R.drawable.image_not_available),
//                    contentDescription = "Failed to load"
//                )
//            }
//        },
//        shimmerParams = ShimmerParams(
//            baseColor = AppPrimaryColor,
//            tilt = 20f,
//            dropOff = 0.65f,
//            durationMillis = 500,
//            highlightColor = AppOnPrimaryColor
//        ),
//    )
//}


//@Composable
//fun CircleCoilImage(url: String, name: String = "", job: String = "") {
//    Column(
//        modifier = Modifier.width(72.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        CoilImage(
//            imageModel = url,
//            modifier = Modifier
//                .size(64.dp)
//                .clip(CircleShape),
//            shimmerParams = ShimmerParams(
//                baseColor = AppPrimaryColor,
//                durationMillis = 500,
//                tilt = 20f,
//                highlightColor = Color.DarkGray
//            ),
//            failure = {
//                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    Icon(
//                        imageVector = Icons.Default.SupervisedUserCircle,
//                        contentDescription = "Filed"
//                    )
//                }
//            },
//            circularReveal = CircularReveal(duration = 1000),
//        )
//        if (name.isNotEmpty() || job.isNotEmpty()) {
//            Text(
//                text = name,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis,
//                style = MaterialTheme.typography.bodyMedium
//            )
//            Text(
//                text = job,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis,
//                style = MaterialTheme.typography.bodySmall
//            )
//        }
//    }
//}