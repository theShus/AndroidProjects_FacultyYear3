package com.example.projekat_septembar.presentation.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.projekat_septembar.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition {

            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)

            finish()
            false
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

}
