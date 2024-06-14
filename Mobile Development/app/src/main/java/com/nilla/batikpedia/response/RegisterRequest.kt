package com.nilla.batikpedia.response

import retrofit2.http.Field

data class RegisterRequest(
    @Field("nama")
    val nama: String,

    @Field("email")
    val email: String,

    @Field("password")
    val password: String
)