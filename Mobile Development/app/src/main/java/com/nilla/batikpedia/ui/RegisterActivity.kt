package com.example.capstone.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nilla.batikpedia.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inisialisasi tombol register
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // Set onClickListener pada tombol login
        btnRegister.setOnClickListener {
            // Intent untuk mengarahkan ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Set onClickListener pada tombol login
        val btnCreateAccount = findViewById<Button>(R.id.btnLoginRegister)
        btnCreateAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}