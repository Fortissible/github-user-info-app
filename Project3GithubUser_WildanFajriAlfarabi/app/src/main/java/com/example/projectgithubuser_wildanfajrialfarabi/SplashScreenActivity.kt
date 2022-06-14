@file:Suppress("DEPRECATION")

package com.example.projectgithubuser_wildanfajrialfarabi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val timer = 1500L
        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },timer)
    }
}