package com.example.capstone.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
<<<<<<< HEAD
=======
import android.widget.Toast
>>>>>>> 5909389e6e28522c40d2102fd9aa382e7f79b466
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.adapter.HomeAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
<<<<<<< HEAD
import com.nilla.batikpedia.ui.Forum
import com.nilla.batikpedia.R
import com.nilla.batikpedia.ui.akun
=======
import com.nilla.batikpedia.Forum
import com.nilla.batikpedia.R
import com.nilla.batikpedia.akun
import com.nilla.batikpedia.data.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
>>>>>>> 5909389e6e28522c40d2102fd9aa382e7f79b466

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