package com.example.myfirebaseexample.api

import com.example.myfirebaseexample.api.response.PostResponse
import com.example.myfirebaseexample.api.response.AyudantiaResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FirebaseApi {
    @GET("Ayudantias.json")
    fun getAyudantias(): Call<MutableMap<String, AyudantiaResponse>>

    @GET("Ayudantias/{id}.json")
    fun getAyudantia(
        @Path("id") id: String
    ): Call<AyudantiaResponse>

    @POST("Ayudantias.json")
    fun setAyudantia(
        @Body() body: AyudantiaResponse
    ): Call<PostResponse>
}