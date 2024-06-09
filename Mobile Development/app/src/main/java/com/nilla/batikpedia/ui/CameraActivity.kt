package com.example.capstone.ui

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nilla.batikpedia.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding

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
    }

    private fun openGallery() {
        // Logic to open gallery
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun openCamera() {
        // Logic to open camera
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun processPhoto() {
        // Logic to process the photo and navigate to the detail page
        Toast.makeText(this, "Proses Foto clicked", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, DetailActivity::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    val selectedImage = data?.data
                    binding.previewImage.setImageURI(selectedImage)
                }
                CAMERA_REQUEST_CODE -> {
                    val photo = data?.extras?.get("data") as Bitmap
                    binding.previewImage.setImageBitmap(photo)
                }
            }
        }
    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 1001
        private const val CAMERA_REQUEST_CODE = 1002
    }
}