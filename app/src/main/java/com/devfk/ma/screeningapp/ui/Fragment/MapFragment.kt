package com.devfk.ma.screeningapp.ui.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.devfk.ma.screeningapp.R
import com.devfk.ma.screeningapp.data.Model.Event
import com.devfk.ma.screeningapp.ui.Component.CarouselAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment() , OnMapReadyCallback , CarouselAdapter.EventListener{

   private var list: ArrayList<Event> = ArrayList()

    var viewPager: ViewPager? = null
    private var onMapReady:Boolean =false;
    private lateinit var mMap: GoogleMap

    companion object {
        @JvmStatic
        fun newInstance(eventList: ArrayList<Event>) = MapFragment().apply {
            arguments = Bundle().apply {
                list.addAll(eventList)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_map, container, false)
        viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        initialization()

        var adapter = CarouselAdapter(list, activity)
        viewPager?.adapter = adapter
        viewPager?.clipToPadding = false
        viewPager?.setPadding(100,0,100,0)
        return view
    }

    private fun initialization() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val first = LatLng(list[0].lat.toDouble(), list[0].long.toDouble())
        changeColorDefault()
        mMap.addMarker(MarkerOptions()
            .position(first)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            .title(list[0].name))
            .showInfoWindow()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(first, 17f))
        setScrolled()
    }

    private fun setScrolled() {
        viewPager?.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val move = LatLng(list[position].lat.toDouble(), list[position].long.toDouble())
                changeColorDefault()
                mMap.addMarker(MarkerOptions()
                    .position(move)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .title(list[position].name)).showInfoWindow()
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(move, 17f))
            }

            override fun onPageSelected(position: Int) {
            }

        })
    }

    private fun changeColorDefault() {
        for (i in list){
            mMap.addMarker(MarkerOptions()
                .position(LatLng(i.lat.toDouble(), i.long.toDouble()))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .title(i.name)).showInfoWindow()
        }
    }

    override fun onEvent(data: Int) {

    }


}

