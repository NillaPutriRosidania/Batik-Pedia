package com.nilla.batikpedia.response

import com.squareup.moshi.Json

data class GenericResponse(
    @Json(name = "status")
    val status: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val data: UserData? = null,
    @Json(name = "code")
    val code: Int? = null
)