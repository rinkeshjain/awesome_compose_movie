package com.rinkesh.composemovie.repository

import com.rinkesh.composemovie.data.remote.ApiService
import com.rinkesh.composemovie.data.remote.responce.GenreResponse
import com.rinkesh.composemovie.utlis.FilmType
import com.rinkesh.composemovie.utlis.ResourceState
import retrofit2.HttpException
import javax.inject.Inject

class GenreFilmRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun genreFilm(filmType: FilmType): ResourceState<GenreResponse> {
        val response = try {
            if (filmType == FilmType.MOVIE)
                apiService.getMovieGenres()
            else apiService.getTvShowGenres()
        } catch (e: Exception) {
            return ResourceState.Error(e.localizedMessage)
        } catch (e: HttpException) {
            return ResourceState.Error(e.localizedMessage)
        }
        return ResourceState.Success(response)
    }
}