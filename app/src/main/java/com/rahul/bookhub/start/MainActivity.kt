package com.rahul.bookhub.start


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.rahul.BookHub.R
import com.rahul.bookhub.DrawerLayout

import android.content.Intent as Intent

class MainActivity : AppCompatActivity() {
    lateinit var etuser : EditText
    lateinit var etpass : EditText
    lateinit var btlog : Button
    lateinit var forgotpass : TextView
    lateinit var create_acc : TextView

    val validmob="8850703968"
    val valpass="rahul"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etuser=findViewById(R.id.etmobile)
        etpass=findViewById(R.id.etusername_pass)
        btlog=findViewById(R.id.btnlogin)
        forgotpass=findViewById(R.id.txtforgotPassword)
        create_acc=findViewById(R.id.txtacc)

        btlog.setOnClickListener {
            val mobile=etuser.text.toString()
            val pass=etpass.text.toString()
            if(mobile==validmob && pass==valpass)
            {
                val intent=Intent(this@MainActivity,DrawerLayout::class.java)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this@MainActivity
                        ,"incorrect increditals",Toast.LENGTH_LONG).show()
            }
        }
        forgotpass.setOnClickListener {
            startActivity(Intent(this@MainActivity, ForgotPassActivity::class.java))
        }
        create_acc.setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateActivity::class.java))
        }

    }


}