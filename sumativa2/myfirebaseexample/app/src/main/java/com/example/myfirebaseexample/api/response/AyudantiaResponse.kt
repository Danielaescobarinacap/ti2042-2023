package com.example.myfirebaseexample.api.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class AyudantiaResponse(

    @SerializedName("01_Capsula") var capsule: String,
    @SerializedName("14_Carrera") var career: String,
    @SerializedName("16_Ayudante") var assistant: String,
    @SerializedName("17_fecha") var date: String
)
