package com.nilla.batikpedia.data

import com.nilla.batikpedia.response.GenericResponse
import com.nilla.batikpedia.response.LoginResponse
import com.nilla.batikpedia.response.NewsDetailResponse
import com.nilla.batikpedia.response.NewsResponse
import com.nilla.batikpedia.response.RegisterResponse
import com.nilla.batikpedia.response.UploadData
import com.nilla.batikpedia.response.UploadResponse
import com.nilla.batikpedia.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
<<<<<<< HEAD
import retrofit2.http.Header
import retrofit2.http.PATCH
=======
import retrofit2.http.Multipart
>>>>>>> 5909389e6e28522c40d2102fd9aa382e7f79b466
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("/api/user/register")
    suspend fun registerUser(
        @Field("nama") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("/api/user/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

<<<<<<< HEAD
    @PATCH("api/user/update")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Field("email") email: String?,
        @Field("nama") nama: String?,
        @Field("password") password: String?,
        @Field("foto") foto: String?
    ): GenericResponse
=======
    @GET("/api/user")
    suspend fun getUserDetails(): UserResponse

    @GET("/api/news")
    suspend fun getNews(): NewsResponse

    @GET("/api/news/{id}")
    suspend fun getNewsDetail(@Path("id") id: String): NewsDetailResponse

    @Multipart
    @POST("/api/batik/scan")
    suspend fun uploadPhoto(@Part image: MultipartBody.Part): UploadResponse<UploadData>
>>>>>>> 5909389e6e28522c40d2102fd9aa382e7f79b466
}