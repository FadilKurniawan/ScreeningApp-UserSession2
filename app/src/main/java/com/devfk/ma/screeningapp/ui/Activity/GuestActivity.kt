package com.devfk.ma.screeningapp.ui.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.error.ANError
import com.devfk.ma.screeningapp.R
import com.devfk.ma.screeningapp.data.Interface.IGuest
import com.devfk.ma.screeningapp.data.Model.DataGuest
import com.devfk.ma.screeningapp.data.Model.Guest
import com.devfk.ma.screeningapp.data.Presenter.GuestPresenter
import com.devfk.ma.screeningapp.ui.Adapter.GuestAdapter
import kotlinx.android.synthetic.main.activity_guest.*
import kotlinx.android.synthetic.main.app_bar.*

class GuestActivity : AppCompatActivity(), AdapterView.OnItemClickListener ,IGuest, View.OnClickListener{
    var resultName:String =""
    var resultAge:Int =0
    var pages = 1
    var per_pages = 10
    var total_pages:Int = 0
    var loadMore = false
    var listData: ArrayList<DataGuest> = ArrayList()
    lateinit var guestAdapter: GuestAdapter
    lateinit var swipe: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest)
        initialization()
        RefreshListener()
        ScrolldownListener()
    }

    private fun ScrolldownListener() {
        gridView.setOnScrollListener(object : AbsListView.OnScrollListener{
            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0 && pages+1 <= total_pages){
                    if(!loadMore){
                        loadingMore.visibility = View.VISIBLE
                        loadMore = true
                        GuestPresenter(this@GuestActivity).getDataGuest(pages+1,per_pages)
                    }
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
//                Toast.makeText(this@GuestActivity,"position: ${gridView.}",Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun initialization() {
        headerText.text = resources.getString(R.string.appBar_guest)
        GuestPresenter(this).getDataGuest(pages,per_pages)
        loadview.visibility = View.VISIBLE
        loadingMore.visibility = View.GONE
        gridView.onItemClickListener = this
        btn_back.setOnClickListener(this)
        btn_media.visibility = View.GONE
        swipe = findViewById<SwipeRefreshLayout>(R.id.swipeContainer)
    }

    private fun RefreshListener() {
        swipe.setOnRefreshListener {
            GuestPresenter(this).getDataGuest(pages,per_pages)
        }
    }

    override fun onBackPressed() {
        if(resultName.isNotEmpty()){
            sendDataBack()
        }
        super.onBackPressed()
    }

    private fun sendDataBack() {
        val intent = Intent().apply {
            putExtra("guestName",resultName)
            putExtra("guestAge",resultAge.toString())
        }
        setResult(Activity.RESULT_OK,intent)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var item: DataGuest = parent?.getItemAtPosition(position) as DataGuest
        resultName = item.firstName + item.lastName
        resultAge = item.id!!
        onBackPressed()
    }

    override fun onGuestList(guest: Guest) {
        if(swipe.isRefreshing){
            swipe.isRefreshing = false
        }

        if(guest.page>pages){
            loadingMore.visibility = View.GONE
            pages = guest.page
            guest.data?.let { listData.addAll(it) }
            guestAdapter.notifyDataSetChanged()
            loadMore = false
        }else {
            total_pages = guest.totalPages
            guest.data?.let { listData.addAll(it) }
            guestAdapter = GuestAdapter(this,listData)
            gridView.adapter =  guestAdapter
            loadview.visibility = View.GONE
        }
    }

    override fun onDataError(error: ANError) {
        if(swipe.isRefreshing){
            swipe.isRefreshing = false
        }
        Toast.makeText(this,"Error: $error",Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> onBackPressed()
         }
    }
}
