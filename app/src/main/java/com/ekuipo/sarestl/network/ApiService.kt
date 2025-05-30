package com.ekuipo.sarestl.network

import com.ekuipo.sarestl.models.EditProfileRequest
import com.ekuipo.sarestl.models.EditProfileResponse
import com.ekuipo.sarestl.models.HistoryRequest
import com.ekuipo.sarestl.models.HistoryResponse
import com.ekuipo.sarestl.models.LoginRequest
import com.ekuipo.sarestl.models.LoginResponse
import com.ekuipo.sarestl.models.NotificationRequest
import com.ekuipo.sarestl.models.NotificationResponse
import com.ekuipo.sarestl.models.RegisterRequest
import com.ekuipo.sarestl.models.RegisterResponse
import com.ekuipo.sarestl.models.ResetPasswordRequest
import com.ekuipo.sarestl.models.ResetPasswordResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody

interface ApiService {
    @POST("/api/login") // Endpoint de login
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/api/resetPasswordApp")
    fun resetPassword (@Body resetPasswordRequest: ResetPasswordRequest): Call<ResetPasswordResponse>

    @POST("/api/register")
    fun register (@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("/api/editProfile")
    fun setEditProfile (@Body editProfileRequest: EditProfileRequest): Call<EditProfileResponse>

    //@POST("/api/getProfile")
    //fun getEditProfile(@Body setEditProfileRequest: EditProfile_getRequest): Call<EditProfile_getResponse>

    @Multipart
    @POST("/upload/")
    suspend fun subirImagen(@Part image: MultipartBody.Part): Response<ResponseBody>

    @POST("/api/get_personal_registration")
    fun get_personal_registration(@Body getPersonalRegistration: HistoryRequest): Call<HistoryResponse>

    @POST("/api/getNotifications")
    fun getNotifications(@Body getNotification: NotificationRequest): Call<NotificationResponse>
}



suspend fun subirImagen(file: File): Boolean {
    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
    val multipartBody = MultipartBody.Part.createFormData("image", file.name, requestBody)

    val retrofit = Retrofit.Builder()
        .baseUrl("https://HugoC.pythonanywhere.com") // Cambia esto
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(ApiService::class.java)
    return try {
        val response = api.subirImagen(multipartBody)
        response.isSuccessful
    } catch (e: Exception) {
        false
    }
}