package com.rahul.bookhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.rahul.BookHub.R
import com.rahul.bookhub.start.MainActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

       Handler().postDelayed({
           startActivity(Intent(this@SplashActivity, MainActivity::class.java))
       },2000)

        }
    }
