package com.example.capstone.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nilla.batikpedia.R
import com.nilla.batikpedia.data.ApiConfig
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailBeritaActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var bodyTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_berita)

        titleTextView = findViewById(R.id.tvTitle)
        dateTextView = findViewById(R.id.tvDate)
        imageView = findViewById(R.id.ivDetailBerita)
        bodyTextView = findViewById(R.id.tvDescription)

        val newsId = intent.getStringExtra("NEWS_ID")
        fetchNewsDetail(newsId)

        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchNewsDetail(newsId: String?) {
        if (newsId == null) {
            Toast.makeText(this, "Invalid news ID", Toast.LENGTH_SHORT).show()
            return
        }

        val apiService = ApiConfig.api
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = apiService.getNewsDetail(newsId)
                if (response.status == "success") {
                    val news = response.data
                    titleTextView.text = news.judul
                    dateTextView.text = news.timestamp
                    Picasso.get().load(news.image).into(imageView)
                    bodyTextView.text = news.body
                } else {
                    Toast.makeText(this@DetailBeritaActivity, "Failed to get news details", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@DetailBeritaActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}