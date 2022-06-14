package com.example.projectgithubuser_wildanfajrialfarabi

import retrofit2.Call
import retrofit2.http.*

interface RetrofitAPIService {
    @GET("search/users")
    @Headers("Authorization: token [INSERT GITHUB API TOKEN HERE]")
    fun getUser(
        @Query("q") q: String
    ): Call<UserSearchResponse>

    @GET("users/{q}")
    @Headers("Authorization: token [INSERT GITHUB API TOKEN HERE]")
    fun getUserDetails(
        @Path("q") q: String
    ): Call<UserDetailsResponse>

    @GET("users/{q}/following")
    @Headers("Authorization: token [INSERT GITHUB API TOKEN HERE]")
    fun getFollowing(
        @Path("q") q: String
    ): Call<List<FollowResponseItem>>

    @GET("users/{q}/followers")
    @Headers("Authorization: token [INSERT GITHUB API TOKEN HERE]")
    fun getFollowers(
        @Path("q") q: String
    ): Call<List<FollowResponseItem>>
}