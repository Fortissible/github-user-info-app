package com.example.projectgithubuser_wildanfajrialfarabi

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favourite: Favourite)

    @Delete
    fun delete(favourite: Favourite)

    @Query("SELECT * from favourite ORDER BY id ASC")
    fun getAllFav(): LiveData<List<Favourite>>
}