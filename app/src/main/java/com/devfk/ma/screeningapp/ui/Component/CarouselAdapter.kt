package com.devfk.ma.screeningapp.ui.Component

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginLeft
import androidx.viewpager.widget.PagerAdapter
import com.devfk.ma.screeningapp.R
import com.devfk.ma.screeningapp.data.Model.Event


class CarouselAdapter(
    list:ArrayList<Event>,
    context: Context?
) : PagerAdapter() {

    private var lisEvent:ArrayList<Event> = list
    private lateinit var layoutInflater: LayoutInflater
    private val context: Context? = context

    interface EventListener {
        fun onEvent(data: Int)
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(context)
        var view =layoutInflater.inflate(R.layout.card_carousel_item,container,false)

        var img = view.findViewById<ImageView>(R.id.imgEvent)
        var title = view.findViewById<TextView>(R.id.txvNameEvent)
        var description = view.findViewById<TextView>(R.id.txvDetailEvent)
        var date = view.findViewById<TextView>(R.id.txvDateEvent)


        img.setImageResource(lisEvent[position].image)
        img.scaleType = ImageView.ScaleType.CENTER_CROP

        title.text = lisEvent[position].name
        description.text = lisEvent[position].detail
        date.text = lisEvent[position].date

        container.addView(view,0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return lisEvent.size
    }
}