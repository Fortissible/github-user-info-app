package com.example.projectgithubuser_wildanfajrialfarabi

import android.content.Context
import androidx.lifecycle.ViewModel
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    companion object{
        private const val TAG = "MainViewModel"
    }
    private val _listUser= MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _listDetail= MutableLiveData<UserDetailsResponse>()
    val listDetail: LiveData<UserDetailsResponse> = _listDetail
    private val _detailIsLoading = MutableLiveData<Boolean>()
    val detailIsLoading: LiveData<Boolean> = _detailIsLoading
    var flag = MutableLiveData(0)

    fun searchUser(userName:String, context: Context){
        _isLoading.value = true
        val client = APIConfig.getApiService().getUser(userName)
        client.enqueue(object : Callback<UserSearchResponse> {
            override fun onResponse(
                call: Call<UserSearchResponse>,
                response: Response<UserSearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()!!.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun searchDetail(userName:String, context: Context){
        _detailIsLoading.value = true
        val client = APIConfig.getApiService().getUserDetails(userName)
        client.enqueue(object : Callback<UserDetailsResponse> {
            override fun onResponse(
                call: Call<UserDetailsResponse>,
                response: Response<UserDetailsResponse>
            ) {
                _detailIsLoading.value = false
                if (response.isSuccessful) {
                    _listDetail.value = response.body()!!
                    setFlag(1)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                _detailIsLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setFlag(flagNya: Int){
        flag.value = flagNya
    }

}