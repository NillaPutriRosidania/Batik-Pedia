package com.nilla.batikpedia.ui
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nilla.batikpedia.R

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavView = findViewById(R.id.bottomNavigationView)
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_beranda -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.menu_detect -> {
                    startActivity(Intent(this, Forum::class.java))
                    true
                }
                R.id.menu_akun -> {
                    startActivity(Intent(this, AkunActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
