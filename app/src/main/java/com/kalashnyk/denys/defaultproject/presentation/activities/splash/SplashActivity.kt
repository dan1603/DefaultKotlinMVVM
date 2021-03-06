package com.kalashnyk.denys.defaultproject.presentation.activities.splash

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.kalashnyk.denys.defaultproject.R
import com.kalashnyk.denys.defaultproject.presentation.activities.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private val timer = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity(MainActivity.newInstance(this))
            finish()
        }, timer)
    }
}
