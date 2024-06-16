package com.example.capstone.ui

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nilla.batikpedia.R
import com.nilla.batikpedia.akun
import com.nilla.batikpedia.data.ApiConfig
import com.nilla.batikpedia.databinding.ActivityCameraBinding
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

import okhttp3.ResponseBody
import retrofit2.HttpException

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private lateinit var bottomNavView: BottomNavigationView
    private var photoBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener {
            openGallery()
        }

        binding.cameraButton.setOnClickListener {
            openCamera()
        }

        binding.processButton.setOnClickListener {
            processPhoto()
        }

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

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun processPhoto() {
        photoBitmap?.let { bitmap ->
            val byteArrayOutputStream = ByteArrayOutputStream()
            val format = Bitmap.CompressFormat.JPEG // You can change this to Bitmap.CompressFormat.PNG if needed
            val formatString = if (format == Bitmap.CompressFormat.JPEG) "jpg" else "png"
            bitmap.compress(format, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val requestBody = RequestBody.create(MediaType.parse("image/$formatString"), byteArray)
            val body = MultipartBody.Part.createFormData("foto", "photo.$formatString", requestBody)

            lifecycleScope.launch {
                try {
                    val response = ApiConfig.api.uploadPhoto(body)
                    if (response.status == "success" && response.data != null) {
                        val data = response.data
                        val intent = Intent(this@CameraActivity, DetailActivity::class.java).apply {
                            putExtra("imageUrl", data.imageUrl)
                            putExtra("nama", data.nama)
                            putExtra("asal", data.asal)
                            putExtra("sejarah", data.sejarah)
                        }
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@CameraActivity, "Failed to upload photo: ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()
                    val errorMessage = errorBody?.string() ?: "Unknown error"
                    Toast.makeText(this@CameraActivity, "HTTP error: ${e.code()} - $errorMessage", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this@CameraActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } ?: run {
            Toast.makeText(this, "No photo to process", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    val selectedImage = data?.data
                    binding.previewImage.setImageURI(selectedImage)
                    selectedImage?.let {
                        photoBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, it)
                    }
                }
                CAMERA_REQUEST_CODE -> {
                    val photo = data?.extras?.get("data") as Bitmap
                    binding.previewImage.setImageBitmap(photo)
                    photoBitmap = photo
                }
            }
        }
    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 1001
        private const val CAMERA_REQUEST_CODE = 1002
    }
}