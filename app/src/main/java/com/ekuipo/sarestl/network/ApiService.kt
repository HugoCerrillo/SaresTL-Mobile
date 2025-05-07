package com.ekuipo.sarestl.network

import com.ekuipo.sarestl.models.LoginRequest
import com.ekuipo.sarestl.models.LoginResponse
import com.ekuipo.sarestl.models.ResetPasswordRequest
import com.ekuipo.sarestl.models.ResetPasswordResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/login") // Endpoint de login
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/api/verifyEmail")
    fun verifyEmail (@Body resetPasswordRequest: ResetPasswordRequest): Call<ResetPasswordResponse>



}