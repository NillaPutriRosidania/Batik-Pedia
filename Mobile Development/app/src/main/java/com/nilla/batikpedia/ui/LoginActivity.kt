package com.example.capstone.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nilla.batikpedia.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inisialisasi tombol login
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Set onClickListener pada tombol login
        btnLogin.setOnClickListener {
            // Intent untuk mengarahkan ke HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // Set onClickListener pada tombol create account
        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)
        btnCreateAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}