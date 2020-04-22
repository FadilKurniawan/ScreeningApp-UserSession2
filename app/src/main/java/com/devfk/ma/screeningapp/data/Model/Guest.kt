package com.devfk.ma.screeningapp.data.Model


import com.google.gson.annotations.SerializedName

data class Guest(
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val total: Int,
    val totalPages: Int,
    @SerializedName("data")
    var data: Array<DataGuest>? = null,
    val ad: Ad
)