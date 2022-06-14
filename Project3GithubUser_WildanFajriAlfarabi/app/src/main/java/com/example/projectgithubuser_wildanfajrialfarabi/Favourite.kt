package com.example.projectgithubuser_wildanfajrialfarabi

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favourite (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "nama")
    var nama: String? = null,

    @ColumnInfo(name = "username")
    var username: String? = null,

    @ColumnInfo(name = "profilepic")
    var profilepic: String? = null,

    @ColumnInfo(name = "following")
    var following: String? = null,

    @ColumnInfo(name = "followers")
    var followers: String? = null,

    @ColumnInfo(name = "company")
    var company: String? = null,

    @ColumnInfo(name = "repository")
    var repository: String? = null,

    @ColumnInfo(name = "location")
    var location: String? = null,

): Parcelable