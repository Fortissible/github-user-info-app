package com.example.projectgithubuser_wildanfajrialfarabi

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class FavouriteAddViewModel(application: Application): ViewModel() {
    private val mFavouriteRepository: FavouriteRepository = FavouriteRepository(application)

    fun insert(favourite: Favourite){
        mFavouriteRepository.insert(favourite)
    }

    fun delete(favourite: Favourite){
        mFavouriteRepository.delete(favourite)
    }

    fun getAllFav(): LiveData<List<Favourite>> = mFavouriteRepository.getAllFav()
}