package com.nilla.batikpedia.ui

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.capstone.ui.CameraActivity
import com.example.capstone.ui.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nilla.batikpedia.R
import com.nilla.batikpedia.preference.Preference
import com.nilla.batikpedia.model.akun.AccountViewModel
import com.nilla.batikpedia.model.akun.AccountViewModelFactory

class AkunActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var imageView: ImageView
    private lateinit var textViewTitle: TextView
    private lateinit var textViewSubtitle: TextView

    private val accountViewModel: AccountViewModel by viewModels {
        AccountViewModelFactory(Preference(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_akun)

        imageView = findViewById(R.id.imageView)
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewSubtitle = findViewById(R.id.textViewSubtitle)
        bottomNavView = findViewById(R.id.bottomNavigationView)

        accountViewModel.userName.observe(this, Observer { name ->
            textViewTitle.text = name ?: ""
        })

        accountViewModel.userEmail.observe(this, Observer { email ->
            textViewSubtitle.text = email ?: ""
        })

        accountViewModel.userPhoto.observe(this, Observer { photoUrl ->
            Glide.with(this)
                .load(photoUrl)
                .placeholder(R.drawable.akun)
                .error(R.drawable.akun)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .into(imageView)
        })


        accountViewModel.loadUser()
        bottomNavView.menu.findItem(R.id.menu_akun).isChecked = true
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
                    true
                }
                else -> false
            }
        }

        findViewById<TextView>(R.id.textViewChangePhoto).setOnClickListener {
            startActivityForResult(Intent(this, UbahProfile::class.java), REQUEST_EDIT_PROFILE)
        }

        findViewById<TextView>(R.id.textViewAboutUs).setOnClickListener {
            startActivity(Intent(this, AboutUs::class.java))
        }

        findViewById<TextView>(R.id.textViewLogOut).setOnClickListener {
            logoutUser()
        }
    }

    override fun onStart() {
        super.onStart()

        accountViewModel.loadUser()

        accountViewModel.userName.observe(this, Observer { name ->
            Log.d(TAG, "Nama Pengguna: $name")
            textViewTitle.text = name ?: ""
        })

        accountViewModel.userEmail.observe(this, Observer { email ->
            Log.d(TAG, "Email Pengguna: $email")
            textViewSubtitle.text = email ?: ""
        })

        accountViewModel.userPhoto.observe(this, Observer { photoUrl ->
            Log.d(TAG, "URL Foto Pengguna: $photoUrl")
            Glide.with(this)
                .load(photoUrl)
                .placeholder(R.drawable.akun)
                .error(R.drawable.akun)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .centerCrop()
                .into(imageView)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_PROFILE && resultCode == Activity.RESULT_OK) {
            // Update UI or reload data if necessary
            accountViewModel.loadUser()
        }
    }

    private fun isCurrentActivity(activityClass: Class<*>): Boolean {
        return activityClass.isInstance(this)
    }

    private fun logoutUser() {
        val preference = Preference(this)
        preference.setLoggedIn(false)
        preference.setToken(null.toString())
        preference.setUsername(null.toString())
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    companion object {
        private const val TAG = "com.nilla.batikpedia.ui.AkunActivity"
        private const val REQUEST_EDIT_PROFILE = 1
    }
}
