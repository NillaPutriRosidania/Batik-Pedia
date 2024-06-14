package com.nilla.batikpedia.ui

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nilla.batikpedia.R
import com.nilla.batikpedia.data.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inisialisasi tombol register
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // Set onClickListener pada tombol login
        btnRegister.setOnClickListener {
            // Intent untuk mengarahkan ke LoginActivity
            val nama = findViewById<EditText>(R.id.etName).text.toString()
            val email = findViewById<EditText>(R.id.etEmail).text.toString()
            val password = etPassword.text.toString()
            registerUser(nama, email, password)
        }

        // Set onClickListener pada tombol login
        val btnCreateAccount = findViewById<Button>(R.id.btnLoginRegister)
        btnCreateAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Handle show/hide password
        etPassword.setOnTouchListener(View.OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = etPassword.compoundDrawables[2]
                if (drawableEnd != null && event.rawX >= (etPassword.right - drawableEnd.bounds.width())) {
                    isPasswordVisible = !isPasswordVisible
                    togglePasswordVisibility(etPassword)
                    return@OnTouchListener true
                }
            }
            false
        })
    }

    private fun togglePasswordVisibility(editText: EditText) {
        if (isPasswordVisible) {
            // Show password
            editText.transformationMethod = null
            editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password, 0, R.drawable.ic_eye_off, 0)
        } else {
            // Hide password
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password, 0, R.drawable.ic_eye, 0)
        }
        editText.setSelection(editText.text.length)
    }

    private fun registerUser(name: String, email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("request", "Registering user: name=$name, email=$email, password=$password")
                val response = ApiConfig.api.registerUser(name, email, password)
                Log.d("response", response.status)
                withContext(Dispatchers.Main) {
                    if (response.status == "success") {
                        Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterActivity, "Please Input Email, Name and Password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}