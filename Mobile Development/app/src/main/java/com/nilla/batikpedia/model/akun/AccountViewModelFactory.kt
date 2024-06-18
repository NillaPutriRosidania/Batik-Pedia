package com.nilla.batikpedia.model.akun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nilla.batikpedia.preference.Preference

class AccountViewModelFactory(private val preference: Preference) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountViewModel(preference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
