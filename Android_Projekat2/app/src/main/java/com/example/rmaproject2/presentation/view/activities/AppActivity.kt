package com.example.rmaproject2.presentation.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.example.rmaproject2.R
import com.example.rmaproject2.presentation.view.recycler.adapter.BottomNavViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView

class AppActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        initViewPager()
        initNavigation()
    }

    private fun initViewPager() {
        viewPager = findViewById(R.id.viewPager)
        viewPager.offscreenPageLimit = 3
        viewPager.adapter = BottomNavViewPager(supportFragmentManager)
    }

    private fun initNavigation() {
        (findViewById<View>(R.id.bottomNavigation) as BottomNavigationView).setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_1 -> viewPager.setCurrentItem(BottomNavViewPager.FRAG_1_COURSE, false)
                R.id.navigation_2 -> viewPager.setCurrentItem(BottomNavViewPager.FRAG_2_NOTES, false)
                R.id.navigation_3 -> viewPager.setCurrentItem(BottomNavViewPager.FRAG_3_STATS, false)
            }
            true
        }
    }

}