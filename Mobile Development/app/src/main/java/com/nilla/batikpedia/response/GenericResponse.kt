package com.nilla.batikpedia.response

import com.squareup.moshi.Json
<<<<<<< HEAD

=======
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
>>>>>>> 929ce59e946fb9d498d6255df2c6e851eb8a67b8
data class GenericResponse(
    @Json(name = "status")
    val status: String,
    @Json(name = "message")
<<<<<<< HEAD
    val message: String,
    @Json(name = "data")
    val data: UserData? = null,
    @Json(name = "code")
    val code: Int? = null
)
=======
    val message: String
)
>>>>>>> 929ce59e946fb9d498d6255df2c6e851eb8a67b8
