package com.example.projectgithubuser_wildanfajrialfarabi

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {
    private val _listFollower= MutableLiveData<List<FollowResponseItem>>()
    val listFollower: LiveData<List<FollowResponseItem>> = _listFollower
    private val _isLoadingFollowers = MutableLiveData<Boolean>()
    val isLoadingFollowers: LiveData<Boolean> = _isLoadingFollowers
    var flag = MutableLiveData(0)

    fun searchFollowers(userName:String, context: Context){
        _isLoadingFollowers.value = true
        val client = APIConfig.getApiService().getFollowers(userName)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoadingFollowers.value = false
                if (response.isSuccessful) {
                    _listFollower.value = response.body()
                    setFlag(1)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoadingFollowers.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setFlag(flagNya: Int){
        flag.value = flagNya
    }

}