package com.example.prak4.model

import com.google.gson.annotations.SerializedName

data class ResponsModel (
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message : String
)