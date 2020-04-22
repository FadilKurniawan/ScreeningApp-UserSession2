package com.devfk.ma.screeningapp.ui.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.devfk.ma.screeningapp.R
import com.devfk.ma.screeningapp.ui.Component.CustomAlertDialog
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() , View.OnClickListener{
    companion object {
        const val EVENT_CODE_ACTIVITY = 0
        const val GUEST_CODE_ACTIVITY = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        initialization()
    }

    private fun initialization() {
        val name:String = intent.getStringExtra("nama")
        val str ="${txvName.text} $name"
        txvName.text = str
        btnEvent.setOnClickListener(this)
        btnGuest.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnEvent->goToEventpage()
            R.id.btnGuest->goToGuestpage()
        }

    }

    private fun goToGuestpage() {
        val intent = Intent(this, GuestActivity::class.java)
        startActivityForResult(intent, GUEST_CODE_ACTIVITY)
    }

    private fun goToEventpage() {
        val intent = Intent(this, EventActivity::class.java)
        startActivityForResult(intent, EVENT_CODE_ACTIVITY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == EVENT_CODE_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){
                val text = data!!.getStringExtra("event")
                btnEvent.text = text
            }
        }else if(requestCode == GUEST_CODE_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){
                val text = data!!.getStringExtra("guestName")
                val num = data!!.getStringExtra("guestAge")
                btnGuest.text = text
                pop(num.toInt())
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun pop(i: Int) {
        var alertDialog:CustomAlertDialog = CustomAlertDialog(this)
        alertDialog.setTitleandContent("Month is ${primaCheck(i)}",getResourceImage(i),"close")
        alertDialog.show()
    }

    private fun primaCheck(month: Int): String {
        if (month>1) {
            for(i in 2..Math.sqrt(month.toDouble()).toInt()) {
                if(month % i == 0) return "Prima"
            }
            return "Prima"
        }
        else return "not Prima"
    }

    private fun getResourceImage(i: Int): Int {
        if(i%2==0 && i%3==0){
            return R.drawable.ic_ios
        }else if(i%2==0){
            return R.drawable.ic_blackberry
        }else if(i%3==0){
            return R.drawable.ic_android
        }else{
            return R.drawable.feature_phone
        }
    }
}
