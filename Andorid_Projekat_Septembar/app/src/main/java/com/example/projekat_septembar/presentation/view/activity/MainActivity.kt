package com.example.projekat_septembar.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.projekat_septembar.R
import com.example.projekat_septembar.databinding.ActivityMainBinding
import com.example.projekat_septembar.presentation.view.recycler.adapters.BottomNavPageAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.toolbar.title = "Newest"
        initViewPager()
        initNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.logoutBtn -> {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
            true
        }
        else -> false
    }

    private fun initViewPager() {
        viewPager = findViewById(R.id.viewPager)
        viewPager.offscreenPageLimit = 3
        viewPager.adapter = BottomNavPageAdapter(supportFragmentManager)
    }

    private fun initNavigation() {
        (findViewById<View>(R.id.bottomNavigation) as BottomNavigationView).setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_1 -> {
                    viewPager.setCurrentItem(BottomNavPageAdapter.FRAG_1_NEWEST, false)
                    binding.appBarMain.toolbar.title = "Newest"
                }
                R.id.navigation_2 -> {
                    viewPager.setCurrentItem(BottomNavPageAdapter.FRAG_2_SEARCH, false)
                    binding.appBarMain.toolbar.title = "Search"
                }
                R.id.navigation_3 -> {
                    viewPager.setCurrentItem(BottomNavPageAdapter.FRAG_3_SAVED, false)
                    binding.appBarMain.toolbar.title = "Saved"
                }
            }
            true
        }
    }

}