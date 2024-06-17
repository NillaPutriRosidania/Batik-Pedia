package com.nilla.batikpedia.response

data class UserResponse(
    val status: String,
    val data: UserData
)

data class UserData(
    val id: String,
    val password: String,
    val foto: String,
    val nama: String,
    val createdAt: String,
    val email: String,
    val updatedAt: String
)

data class UserDataToUpdate(
    val nama: String?,
    val email: String?,
    val password: String?
)

