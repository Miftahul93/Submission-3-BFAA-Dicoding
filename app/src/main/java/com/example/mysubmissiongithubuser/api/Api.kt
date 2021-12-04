package com.example.mysubmissiongithubuser.api

import com.example.mysubmissiongithubuser.model.DetailUserResponse
import com.example.mysubmissiongithubuser.model.User
import com.example.mysubmissiongithubuser.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
            @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
            @Path("username") usernama: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
            @Path("username") usernama: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
            @Path("username") username: String
    ): Call<ArrayList<User>>
}