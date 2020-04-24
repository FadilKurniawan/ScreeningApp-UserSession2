package com.devfk.ma.screeningapp.ui.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.devfk.ma.screeningapp.R
import com.devfk.ma.screeningapp.data.Model.Event
import com.devfk.ma.screeningapp.ui.Adapter.EventAdapter
import com.devfk.ma.screeningapp.ui.Fragment.MapFragment
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.app_bar.*

class EventActivity : AppCompatActivity() ,AdapterView.OnItemClickListener,View.OnClickListener{

    var result:String =""
    var eventList:ArrayList<Event> = ArrayList<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        initialization()
    }

    private fun initialization() {
        headerText.text = resources.getString(R.string.appBar_event)
        fragment_container.visibility = View.GONE
        eventList.add(
            Event("Pekan #KreatifDisaatSulit", "April 04 2020", R.drawable.event1,
                mutableListOf("#nutricia","#highlight F3"),resources.getString(R.string.txv_detail),
                "-6.901267", "107.618681"
            )
        )
        eventList.add(
            Event("Saturasi Volume 1", "April 03  2020", R.drawable.event3,
                mutableListOf("#nutricia","#highlight F3"),resources.getString(R.string.txv_detail),
                "-6.910463", "107.619621"
            )
        )
        eventList.add(
            Event("Charity Selection Night", "March 23 2020", R.drawable.event2,
                mutableListOf("#nutricia","#event"),resources.getString(R.string.txv_detail),
                "-6.908583", "107.616208"
            )
        )
        eventList.add(
            Event("ANGKLUNG MUSIC CONCERT", " March 20 2020", R.drawable.event4,
                mutableListOf("#nutricia","#highlight F3","#event"),resources.getString(R.string.txv_detail),
                "-6.917161", "107.629460"
            )
        )

        lv_event.adapter = EventAdapter(eventList)
        lv_event.onItemClickListener = this

        btn_back.setOnClickListener(this)
        btn_media.setOnClickListener(this)
        btn_search.setOnClickListener(this)
        btn_media.visibility = View.VISIBLE
        btn_search.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if(result.isNotEmpty()){
            sendDataBack()
        }
        if(supportFragmentManager.backStackEntryCount>0){
            btn_media.setBackgroundResource(R.drawable.ic_map_view)
        }
        super.onBackPressed()
    }

    private fun sendDataBack() {
        val intent = Intent().apply {
            putExtra("event",result)
        }
        setResult(Activity.RESULT_OK,intent)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var item:Event = parent?.getItemAtPosition(position) as Event
        result = item.name
        onBackPressed()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back ->{
                onBackPressed()
            }
            R.id.btn_media ->{
                if(fragment_container.visibility==View.GONE||supportFragmentManager.backStackEntryCount==0) {
                    onMediaClick()
                    btn_media.setBackgroundResource(R.drawable.ic_list_view)
                }else{
                    onBackPressed()
                    btn_media.setBackgroundResource(R.drawable.ic_map_view)
                }
            }
        }
    }

    private fun onMediaClick() {
            fragment_container.visibility = View.VISIBLE
            val mapFragment = MapFragment.newInstance(eventList)
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.fragment_container,mapFragment)
            transaction.addToBackStack(null)
            transaction.commit()
    }
}
