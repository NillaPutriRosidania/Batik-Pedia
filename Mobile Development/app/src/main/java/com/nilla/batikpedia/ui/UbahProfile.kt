package com.nilla.batikpedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nilla.batikpedia.R
import com.nilla.batikpedia.data.ApiConfig
import com.nilla.batikpedia.databinding.ActivityUbahProfileBinding
import com.nilla.batikpedia.model.akun.AccountViewModel
import com.nilla.batikpedia.model.akun.AccountViewModelFactory
import com.nilla.batikpedia.preference.Preference
import com.nilla.batikpedia.response.UserDataToUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class UbahProfile : AppCompatActivity() {

    private lateinit var binding: ActivityUbahProfileBinding
    private lateinit var preference: Preference
    private lateinit var viewModel: AccountViewModel
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUbahProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = Preference(this)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        viewModel = ViewModelProvider(this, AccountViewModelFactory(preference)).get(
            AccountViewModel::class.java)

        val fotoUrl = preference.getUserPhoto() ?: "https://default-url-for-avatar"

        Glide.with(this)
            .load(fotoUrl)
            .placeholder(R.drawable.akun)
            .error(R.drawable.akun)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .centerCrop()
            .into(binding.imageView)

        binding.editTextTextNama.setText(preference.getUsername() ?: "")
        binding.editTextTextEmailAddress.setText(preference.getUserEmail() ?: "")

        binding.textViewUpload.setOnClickListener {
            openGalleryForImage()
        }

        binding.buttonSimpan.setOnClickListener {
            val nama = binding.editTextTextNama.text.toString().trim()
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Nama, Email, dan Password harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            selectedImageUri?.let { uri ->
                val file = createTempImageFile(uri)
                if (file != null) {
                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    val imagePart = MultipartBody.Part.createFormData("foto", file.name, requestFile)
                    updateProfileWithImage(nama, email, password, imagePart)
                } else {
                    showToast("Gagal membuat file sementara dari gambar")
                }
            } ?: run {
                updateProfileWithoutImage(nama, email, password)
            }
        }

        binding.editTextTextPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.buttonSimpan.isEnabled = !s.isNullOrBlank()
            }
        })
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImage = result.data?.data
                selectedImage?.let {
                    binding.imageView.setImageURI(it)
                    selectedImageUri = it
                }
            }
        }

    private fun updateProfileWithImage(nama: String, email: String, password: String, imagePart: MultipartBody.Part) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val requestBodyNama = nama.toRequestBody("text/plain".toMediaTypeOrNull())
                val requestBodyEmail = email.toRequestBody("text/plain".toMediaTypeOrNull())
                val requestBodyPassword = password.toRequestBody("text/plain".toMediaTypeOrNull())

                val response = ApiConfig.api.updateUserProfile(
                    imagePart,
                    requestBodyNama,
                    requestBodyEmail,
                    requestBodyPassword
                )

                if (response.status == "success") {
                    withContext(Dispatchers.Main) {
                        val fotoUrl = response.data?.foto ?: preference.getUserPhoto()
                        viewModel.updateUserData(nama, email, fotoUrl)
                        navigateToAkunActivity()
                    }
                    showToast("Profil berhasil diperbarui")
                } else {
                    showToast("Gagal memperbarui profil: ${response.message}")
                }
            } catch (e: Exception) {
                showToast("Terjadi kesalahan: ${e.message}")
            }
        }
    }

    private fun updateProfileWithoutImage(nama: String, email: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val requestBodyNama = nama.toRequestBody("text/plain".toMediaTypeOrNull())
                val requestBodyEmail = email.toRequestBody("text/plain".toMediaTypeOrNull())
                val requestBodyPassword = password.toRequestBody("text/plain".toMediaTypeOrNull())

                val response = ApiConfig.api.updateUserProfileWithoutPhoto(
                    UserDataToUpdate(nama, email, password)
                )

                if (response.status == "success") {
                    withContext(Dispatchers.Main) {
                        viewModel.updateUserData(nama, email, null)
                        navigateToAkunActivity()
                    }
                    showToast("Profil berhasil diperbarui")
                } else {
                    showToast("Gagal memperbarui profil: ${response.message}")
                }
            } catch (e: Exception) {
                showToast("Terjadi kesalahan: ${e.message}")
            }
        }
    }

    private fun createTempImageFile(uri: Uri): File {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val file = File(cacheDir, "temp_image.jpg")
        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    private fun navigateToAkunActivity() {
        val intent = Intent(this@UbahProfile, AkunActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            Toast.makeText(this@UbahProfile, message, Toast.LENGTH_SHORT).show()
        }
    }
}
