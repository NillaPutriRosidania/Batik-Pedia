package com.nilla.batikpedia.data
import com.nilla.batikpedia.response.GenericResponse
import com.nilla.batikpedia.response.LoginResponse
import com.nilla.batikpedia.response.NewsDetailResponse
import com.nilla.batikpedia.response.NewsResponse
import com.nilla.batikpedia.response.RegisterResponse
import com.nilla.batikpedia.response.UploadData
import com.nilla.batikpedia.response.UploadResponse
import com.nilla.batikpedia.response.UserDataToUpdate
import com.nilla.batikpedia.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.*
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

    @GET("/api/user")
    suspend fun getUserDetails(): UserResponse

    @GET("/api/news")
    suspend fun getNews(): NewsResponse

    @GET("/api/news/{id}")
    suspend fun getNewsDetail(@Path("id") id: String): NewsDetailResponse

    @Multipart
    @POST("/api/batik/scan")
    suspend fun uploadPhoto(@Part image: MultipartBody.Part): UploadResponse<UploadData>

    @Multipart
    @PATCH ("/api/user/update")
    suspend fun updateUserProfile(
        @Part foto: MultipartBody.Part?,
        @Part("nama") name: okhttp3.RequestBody?,
        @Part("email") email: okhttp3.RequestBody?,
        @Part("password") password: okhttp3.RequestBody?
    ): GenericResponse

    @PATCH("/api/user/update")
    suspend fun updateUserProfileWithoutPhoto(
        @Body userData: UserDataToUpdate
    ): GenericResponse
}
