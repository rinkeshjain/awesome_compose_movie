package com.rinkesh.composemovie.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rinkesh.composemovie.data.remote.ApiService
import com.rinkesh.composemovie.model.Film
import com.rinkesh.composemovie.utlis.FilmType
import retrofit2.HttpException
import java.io.IOException

class TopRatedFilmSource(private val apiService: ApiService, private val filmType: FilmType) :
    PagingSource<Int, Film>() {

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        return try {
            val nextPage = params.key ?: 1
            val treadingFilmData = if (filmType == FilmType.MOVIE)
                apiService.getTopRatedMovies(page = nextPage)
            else
                apiService.getTopRatedTvSeries(page = nextPage)
            LoadResult.Page(
                nextKey = if (treadingFilmData.results.isEmpty()) null else nextPage + 1,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                data = treadingFilmData.results
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}


