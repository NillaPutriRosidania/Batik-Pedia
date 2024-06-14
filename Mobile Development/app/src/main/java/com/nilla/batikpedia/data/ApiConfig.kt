package com.nilla.batikpedia.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiConfig {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://batik-pedia-api-7buytimhqq-et.a.run.app")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}