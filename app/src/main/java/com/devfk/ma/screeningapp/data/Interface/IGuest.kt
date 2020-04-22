package com.devfk.ma.screeningapp.data.Interface

import com.androidnetworking.error.ANError
import com.devfk.ma.screeningapp.data.Model.Guest

interface IGuest {
    fun onGuestList(guest: Guest)
    fun onDataError(error: ANError)
}