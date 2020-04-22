package com.devfk.ma.screeningapp.data.Model


import com.google.gson.annotations.SerializedName

data class DataGuest(
    @SerializedName("id")
    var id: Int?=null,
    @SerializedName("email")
    var email: String?=null,
    @SerializedName("first_name")
    var firstName: String?=null,
    @SerializedName("last_name")
    var lastName: String?=null,
    @SerializedName("avatar")
    var avatar: String?=null
)