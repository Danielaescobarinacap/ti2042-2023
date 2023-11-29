package com.example.myfirebaseexample.api

import com.example.myfirebaseexample.api.response.PostResponse
import com.example.myfirebaseexample.api.response.AyudantiaResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body

class FirebaseApiAdapter {
    private var URL_BASE = "https://cyberpunk-database.firebaseio.com/"
    private val firebaseApi = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getAyudantias(): MutableMap<String, AyudantiaResponse>? {
        val call = firebaseApi.create(FirebaseApi::class.java).getAyudantias().execute()
        val Ayudantias = call.body()
        return Ayudantias
    }

    fun getAyudantia(id: String): AyudantiaResponse? {
        val call = firebaseApi.create(FirebaseApi::class.java).getAyudantia(id).execute()
        val Ayudantia = call.body()
        return Ayudantia
    }

    fun setAyudantia(ayudantia: AyudantiaResponse): PostResponse? {
        val call = firebaseApi.create(FirebaseApi::class.java).setAyudantia(ayudantia).execute()
        val results = call.body()
        return results
    }
}