package com.example.projectgithubuser_wildanfajrialfarabi

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavouriteRepository(application: Application) {
    private val mFavouriteDao: FavouriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init{
        val db = FavouriteRoomDatabase.getDatabase(application)
        mFavouriteDao = db.favouriteDao()
    }

    fun getAllFav(): LiveData<List<Favourite>> = mFavouriteDao.getAllFav()

    fun insert(favourite: Favourite){
        executorService.execute{(mFavouriteDao.insert(favourite))}
    }

    fun delete(favourite: Favourite){
        executorService.execute{mFavouriteDao.delete(favourite)}
    }
}