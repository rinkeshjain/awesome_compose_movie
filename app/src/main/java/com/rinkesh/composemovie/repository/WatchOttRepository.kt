package com.rinkesh.composemovie.repository


import com.rinkesh.composemovie.data.remote.ApiService
import com.rinkesh.composemovie.data.remote.responce.WatchProviderResponse
import com.rinkesh.composemovie.utlis.FilmType
import com.rinkesh.composemovie.utlis.ResourceState
import retrofit2.HttpException
import javax.inject.Inject

class WatchOttRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getWatchOtt(
        mediaId: Int,
        filmType: FilmType
    ): ResourceState<WatchProviderResponse> {
        val data = try {
            apiService.getWatchProviders(
                filmPath = if (filmType == FilmType.MOVIE) "movie" else "tv",
                filmId = mediaId
            )
        } catch (e: Exception) {
            return ResourceState.Error(e.localizedMessage)
        } catch (e: HttpException) {
            return ResourceState.Error(e.localizedMessage)
        }
        return ResourceState.Success(data)
    }
}