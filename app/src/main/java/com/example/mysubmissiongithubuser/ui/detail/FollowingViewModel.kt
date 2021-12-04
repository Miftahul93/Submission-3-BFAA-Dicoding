package com.example.mysubmissiongithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysubmissiongithubuser.api.RetrofitClient
import com.example.mysubmissiongithubuser.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    val listFollowings = MutableLiveData<ArrayList<User>>()

    fun setListFollowings(username: String){
        RetrofitClient.apiInstance
                .getFollowing(username)
                .enqueue(object : Callback<ArrayList<User>>{
                    override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                        if (response.isSuccessful){
                            listFollowings.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                        Log.d("Failure", t.message)
                    }
                })
    }

    fun getListFollowings(): LiveData<ArrayList<User>>{
        return listFollowings
    }
}