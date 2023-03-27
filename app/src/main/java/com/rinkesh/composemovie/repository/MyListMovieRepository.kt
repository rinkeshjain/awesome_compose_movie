package com.rinkesh.composemovie.repository

import com.rinkesh.composemovie.data.local.MovieDao
import com.rinkesh.composemovie.data.local.MyListMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyListMovieRepository @Inject constructor(private val movieDao: MovieDao) {
    suspend fun insertMovieInList(myListMovie: MyListMovie) {
        movieDao.insertMovieInList(myListMovie = myListMovie)
    }

    suspend fun removeFromList(mediaId: Int) {
        movieDao.removeFromList(mediaId)
    }

    suspend fun deleteList() {
        movieDao.deleteList()
    }

    suspend fun exist(mediaId: Int): Int {
        return movieDao.exists(mediaId)
    }

    fun getAllData(): Flow<List<MyListMovie>> {
        return movieDao.getAllWatchListData()
    }
}