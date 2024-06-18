package com.nilla.batikpedia.model.akun

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nilla.batikpedia.preference.Preference

class AccountViewModel(private val preference: Preference) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String>
        get() = _userEmail

    private val _userPhoto = MutableLiveData<String>()
    val userPhoto: LiveData<String>
        get() = _userPhoto

    init {
        loadUser()
    }

    fun loadUser() {
        _userName.value = preference.getUsername()
        _userEmail.value = preference.getUserEmail()
        _userPhoto.value = preference.getUserPhoto()
    }

    fun updateUserData(name: String, email: String, photoUrl: String?) {
        _userName.value = name
        _userEmail.value = email
        photoUrl?.let {
            _userPhoto.value = it
            preference.setUserPhoto(it)
        }
        preference.setUsername(name)
        preference.setUserEmail(email)
    }
}
