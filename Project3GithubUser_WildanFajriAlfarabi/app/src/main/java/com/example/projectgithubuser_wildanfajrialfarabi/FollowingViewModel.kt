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

class FollowingViewModel: ViewModel() {
    private val _listFollowing= MutableLiveData<List<FollowResponseItem>>()
    val listFollowing: LiveData<List<FollowResponseItem>> = _listFollowing
    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing
    var flag = MutableLiveData(0)

    fun searchFollowing(userName:String,context: Context){
        _isLoadingFollowing.value = true
        val client = APIConfig.getApiService().getFollowing(userName)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoadingFollowing.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                    setFlag(1)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoadingFollowing.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setFlag(flagNya: Int){
        flag.value = flagNya
    }

}