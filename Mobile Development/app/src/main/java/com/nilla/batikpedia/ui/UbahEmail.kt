package com.nilla.batikpedia.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nilla.batikpedia.R
import com.nilla.batikpedia.data.ApiConfig
import com.nilla.batikpedia.preference.Preference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UbahEmail : AppCompatActivity() {

    private lateinit var preference: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_email)

        preference = Preference(this)

        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val saveButton = findViewById<Button>(R.id.buttonSimpan)

        saveButton.setOnClickListener {
            val newEmail = emailEditText.text.toString()
            if (newEmail.isNotEmpty()) {
                changeEmail(newEmail)
            } else {
                Toast.makeText(this, "Please enter a new email address", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changeEmail(newEmail: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val token = preference.getToken()
                if (token != null) {
                    val response = ApiConfig.api.updateUser(
                        "Bearer $token",
                        newEmail,
                        null,
                        null,
                        null
                    )
                    withContext(Dispatchers.Main) {
                        if (response.status == "success") {
                            Toast.makeText(this@UbahEmail, "Email updated successfully", Toast.LENGTH_SHORT).show()
                            preference.setUsername(newEmail)
                        } else {
                            Toast.makeText(this@UbahEmail, "Failed to update email: ${response.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@UbahEmail, "User not logged in", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UbahEmail, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
