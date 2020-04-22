package com.devfk.ma.screeningapp.data.Presenter

import android.content.Context
import android.provider.ContactsContract
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener
import com.devfk.ma.screeningapp.data.Interface.IGuest
import com.devfk.ma.screeningapp.data.Model.Ad
import com.devfk.ma.screeningapp.data.Model.DataGuest
import com.devfk.ma.screeningapp.data.Model.Guest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Response
import org.json.JSONObject


class GuestPresenter (context:Context){
    val GuestView = context as IGuest
    val BASE_URL =  "https://reqres.in/api/users"

    fun getDataGuest(page: Int, per_page: Int){
        AndroidNetworking.get(BASE_URL)
            .addQueryParameter("page",page.toString())
            .addQueryParameter("per_page",per_page.toString())
            .setPriority(Priority.HIGH)
            .build()
            .getAsOkHttpResponseAndJSONObject( object :OkHttpResponseAndJSONObjectRequestListener{
                override fun onResponse(okHttpResponse: Response?, response: JSONObject?) {
                    var gson = Gson()
                    var type = object : TypeToken<Array<DataGuest>>() {}.type
                    var arr = response!!.getString("data")
                    var list:Array<DataGuest> = gson.fromJson(arr, type)

                    var guest = Guest(
                        response!!.getInt("page"),
                        response!!.getInt("per_page"),
                        response!!.getInt("total"),
                        response!!.getInt("total_pages"),
                        list,
                        Ad(
                            response!!.getJSONObject("ad").getString("company"),
                            response!!.getJSONObject("ad").getString("url"),
                            response!!.getJSONObject("ad").getString("text")
                        )
                    )
                    GuestView.onGuestList(guest)
                }

                override fun onError(anError: ANError?) {
                    TODO("Not yet implemented")
                }

            })
//            .getAsObject(Guest::class.java, object :
//                ParsedRequestListener<Guest> {
//                override fun onResponse(response: Guest) {
//                    println("*** resp : $response")
//                    GuestView.onGuestList(response)
//                }
//                override fun onError(error: ANError) { // handle error
//                    GuestView.onDataError(error)
//                }
//            })
    }
}