package com.nilla.batikpedia.data

import com.nilla.batikpedia.response.GenericResponse
import com.nilla.batikpedia.response.LoginResponse
import com.nilla.batikpedia.response.RegisterResponse
import com.nilla.batikpedia.response.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

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

    @PATCH("api/user/update")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Field("email") email: String?,
        @Field("nama") nama: String?,
        @Field("password") password: String?,
        @Field("foto") foto: String?
    ): GenericResponse
}