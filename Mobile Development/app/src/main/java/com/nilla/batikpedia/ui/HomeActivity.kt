package com.example.capstone.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.adapter.HomeAdapter
import com.example.capstone.model.Item
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nilla.batikpedia.ui.Forum
import com.nilla.batikpedia.R
import com.nilla.batikpedia.ui.akun

class HomeActivity : AppCompatActivity() {

    //bottom navigasi
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val textView: TextView = findViewById(R.id.tvCamera)
        textView.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = findViewById(R.id.rvHome)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val itemList = generateDummyList()
        val adapter = HomeAdapter(itemList)
        recyclerView.adapter = adapter

        //bottom navigasi
        bottomNavView = findViewById(R.id.bottomNavigationView)
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_beranda -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.menu_forum -> {
                    startActivity(Intent(this, Forum::class.java))
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

    //berita dummy list
    private fun generateDummyList(): List<Item> {
        val list = ArrayList<Item>()
        list.add(Item("Rabu, 5 Juni 2024", R.drawable.berita, "Gaya Chris Martin Beskapan, Motif Batik Bintang Terinspirasi Filosofi Jawa"))
        list.add(Item("Kamis, 6 Mei 2024", R.drawable.berita, "Gaya Lain yang Terinspirasi Filosofi Jawa"))
        list.add(Item("Jumat, 7 Mei 2024", R.drawable.berita, "Seekor Rusa terlihat menyeberangi sungai"))
        list.add(Item("Jumat, 7 Mei 2024", R.drawable.berita, "Seekor Rusa terlihat menyeberangi sungai"))
        list.add(Item("Jumat, 7 Mei 2024", R.drawable.berita, "Seekor Rusa terlihat menyeberangi sungai"))
        list.add(Item("Jumat, 7 Mei 2024", R.drawable.berita, "Seekor Rusa terlihat menyeberangi sungai"))
        list.add(Item("Jumat, 7 Mei 2024", R.drawable.berita, "Seekor Rusa terlihat menyeberangi sungai"))
        return list
    }
}