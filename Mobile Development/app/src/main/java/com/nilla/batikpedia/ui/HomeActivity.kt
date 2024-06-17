package com.example.capstone.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.adapter.HomeAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nilla.batikpedia.R
import com.nilla.batikpedia.ui.AkunActivity
import com.nilla.batikpedia.data.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var adapter: HomeAdapter
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recyclerView: RecyclerView = findViewById(R.id.rvHome)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        adapter = HomeAdapter(emptyList(), this)
        recyclerView.adapter = adapter

        fetchDataFromApi()

        bottomNavView = findViewById(R.id.bottomNavigationView)
        bottomNavView.menu.findItem(R.id.menu_beranda).isChecked = true
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_beranda -> {
                    if (!isCurrentActivity(HomeActivity::class.java)) {
                        startActivity(Intent(this, HomeActivity::class.java))
                    }
                    true
                }
                R.id.menu_detect -> {
                    if (!isCurrentActivity(CameraActivity::class.java)) {
                        startActivity(Intent(this, CameraActivity::class.java))
                    }
                    true
                }
                R.id.menu_akun -> {
                    if (!isCurrentActivity(AkunActivity::class.java)) {
                        startActivity(Intent(this, AkunActivity::class.java))
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun isCurrentActivity(activityClass: Class<*>): Boolean {
        return activityClass.isInstance(this)
    }

    private fun fetchDataFromApi() {
        val apiService = ApiConfig.api
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = apiService.getNews()
                if (response.status == "success") {
                    adapter.updateData(response.data)
                } else {
                    Toast.makeText(this@HomeActivity, "Gagal mendapatkan data berita", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@HomeActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
