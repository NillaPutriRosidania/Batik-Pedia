package com.example.capstone.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nilla.batikpedia.R
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imageView: ImageView = findViewById(R.id.ivDetail)
        val nameTextView: TextView = findViewById(R.id.tvTitle)
        val originTextView: TextView = findViewById(R.id.tvLocation)
        val historyTextView: TextView = findViewById(R.id.tvDescription)
        val backButton: ImageView = findViewById(R.id.backButton)

        val imageUrl = intent.getStringExtra("imageUrl")
        val name = intent.getStringExtra("nama")
        val origin = intent.getStringExtra("asal")
        val history = intent.getStringExtra("sejarah")

        if (imageUrl != null) {
            Picasso.get().load(imageUrl).into(imageView) // Using Picasso to load the image
        }
        nameTextView.text = name
        originTextView.text = origin
        historyTextView.text = history

        backButton.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }
}