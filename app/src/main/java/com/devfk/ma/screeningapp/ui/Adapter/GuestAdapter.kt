package com.devfk.ma.screeningapp.ui.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.devfk.ma.screeningapp.R
import com.devfk.ma.screeningapp.data.Model.DataGuest
import com.devfk.ma.screeningapp.data.Model.Guest
import com.squareup.picasso.Picasso


class GuestAdapter(context: Context, nameItem: ArrayList<DataGuest>) : BaseAdapter(){
    private val item = nameItem
    lateinit var layout:RelativeLayout

    @SuppressLint("ViewHolder")
    override fun getView(position:Int, convertView: View?, parent: ViewGroup?):View{
        // Inflate the custom view
        val inflater = parent?.context?.
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view    = inflater.inflate(R.layout.grid_item,null)
        // Get the custom view widgets reference
        val name = view.findViewById<TextView>(R.id.txvNameGuest)
        var img = view.findViewById<ImageView>(R.id.imgGuest)
        layout = view.findViewById<RelativeLayout>(R.id.grid_layout)

        name.text = item[position].firstName + item[position].lastName



        var  param: RelativeLayout.LayoutParams =
            RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                Gravity.CENTER
            }

        Picasso.get()
            .load(item[position].avatar)
            .placeholder(R.drawable.ic_guest)
            .error(R.drawable.ic_error)
            .into(img)

        layout.layoutParams = param
        return view
    }

    override fun getItem(position: Int): Any? {
        return item[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Count the items
    override fun getCount(): Int {
        return item.size
    }

}