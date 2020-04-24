package com.devfk.ma.screeningapp.ui.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.devfk.ma.screeningapp.R
import com.devfk.ma.screeningapp.ui.Component.CustomAlertDialog
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.form_login.*

class HomeActivity : AppCompatActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initialization()
    }

    private fun initialization() {
        btn_next.setOnClickListener(this)
        btn_check.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_next -> {
                val intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("nama",edtName.text.toString())
                startActivity(intent)
            }

            R.id.btn_check -> nextPage(edtPalindrome.text)
        }
    }

    private fun nextPage(text: Editable?) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(checkPolindrome(text.toString()))
        builder.setNeutralButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun checkPolindrome(str: String): String {
        var string = str.replace(" ","")
        var reverse = string.reversed()
        if(string.equals(reverse)){
            return "is Polindrome"
        }else{
            return "not Polindrome"
        }

    }
}
