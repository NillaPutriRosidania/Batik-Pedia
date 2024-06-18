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
import com.example.capstone.ui.HomeActivity
import com.nilla.batikpedia.R
import com.nilla.batikpedia.data.ApiConfig
import com.nilla.batikpedia.preference.Preference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private var isPasswordVisible = false
    private lateinit var preference: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        preference = Preference(this)

        if (preference.isLoggedIn()) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        // Set onClickListener pada tombol login
        btnLogin.setOnClickListener {
            val email = findViewById<EditText>(R.id.etEmail).text.toString()
            val password = etPassword.text.toString()
            loginUser(email, password)
        }

        // Set onClickListener pada tombol create account
        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)
        btnCreateAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Handle show/hide password
        etPassword.setOnTouchListener(View.OnTouchListener { v, event ->
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

    private fun loginUser(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("request", "Login user: email=$email, password=$password")
                val response = ApiConfig.api.loginUser(email, password)
                Log.d("response", response.status)
                withContext(Dispatchers.Main) {
                    if (response.status == "success") {
                        // Save the token in SharedPreferences
                        preference.setToken(response.token)
                        // Save login status
                        preference.setLoggedIn(true)

                        // Fetch user details and save to preferences
                        fetchUserDetails()

                        Toast.makeText(this@LoginActivity, response.message, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, response.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "Please Input Email and Password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchUserDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiConfig.api.getUserDetails()
                withContext(Dispatchers.Main) {
                    val userDetails = response.data
                    Log.d("UserDetails", "ID: ${userDetails.id}, Name: ${userDetails.nama}, Email: ${userDetails.email}")

                    // Save user details to preferences
                    preference.setUsername(userDetails.nama)
                    preference.setUserEmail(userDetails.email)
                    preference.setUserPhoto(userDetails.foto)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "Failed to fetch user details: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("fetchUserDetails", "Exception: ", e)
                }
            }
        }
    }
}
