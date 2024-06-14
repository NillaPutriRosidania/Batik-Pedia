package com.nilla.batikpedia.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "status")
    val status: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "token")
    val token : String,
)