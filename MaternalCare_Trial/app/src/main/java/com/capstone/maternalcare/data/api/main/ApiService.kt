package com.capstone.maternalcare.data.api.main

import com.capstone.maternalcare.data.model.ResponseAllHistory
import com.capstone.maternalcare.data.model.ResponseData
import com.capstone.maternalcare.data.model.ResponseHistory
import com.capstone.maternalcare.data.model.ResponseUpdate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Call<ResponseData>

    @POST("register")
    fun register(
        @Query("username") username: String,
        @Query("full_name") full_name: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("telephone") telephone: String,
        @Query("date_of_birth") date_of_birth: String,
    ): Call<ResponseData>

    @POST("users/changePassword")
    fun checkPassword(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Call<ResponseData>

    @POST("email")
    fun checkEmail(
        @Query("email") email: String,
    ): Call<ResponseData>

    @PUT("users/{id}")
    fun updateUser(
        @Path("id") id: Int,
        @Query("username") username: String,
        @Query("full_name") full_name: String,
        @Query("email") email: String,
        @Query("telephone") telephone: String,
        @Query("date_of_birth") date_of_birth: String,
    ): Call<ResponseUpdate>

    @GET("users/username")
    fun checkUser(
        @Query("username") username: String,
    ): Call<ResponseData>

    @GET("history")
    fun getAllHistory(
    ): Call<ResponseAllHistory>

    @POST("history")
    fun sendHistory(
        @Query("day_to_heal") day_to_heal: Int,
        @Query("date_to_heal") date_to_heal: String,
        @Query("status") status: String,
        @Query("recommendations") recommendations: String,
        @Query("id_user") id_user: Int,
        @Query("id_diagnose") id_diagnose: Int,
    ): Call<ResponseHistory>

    @GET("reset-password")
    fun resetPassword(
        @Query("email") email: String,
        @Query("new_password") new_password: String,
        @Query("confirm_password") confirm_password: String,
    ): Call<ResponseData>

}
