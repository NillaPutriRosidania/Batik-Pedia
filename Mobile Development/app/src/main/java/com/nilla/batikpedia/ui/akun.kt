package com.nilla.batikpedia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstone.ui.CameraActivity
import com.example.capstone.ui.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nilla.batikpedia.R

class akun : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_akun)


        bottomNavView = findViewById(R.id.bottomNavigationView)
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_beranda -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.menu_detect -> {
                    startActivity(Intent(this, CameraActivity::class.java))
                    true
                }
                R.id.menu_akun -> {
                    startActivity(Intent(this, akun::class.java))
                    true
                }
                else -> false
            }
        }
    }
    }