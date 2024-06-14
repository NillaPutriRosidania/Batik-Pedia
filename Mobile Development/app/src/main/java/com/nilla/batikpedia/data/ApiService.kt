package com.nilla.batikpedia.data

import com.nilla.batikpedia.response.LoginResponse
import com.nilla.batikpedia.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
}