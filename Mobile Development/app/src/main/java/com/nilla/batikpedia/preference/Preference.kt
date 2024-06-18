package com.nilla.batikpedia.preference

import android.content.Context
import android.content.SharedPreferences

class Preference(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val IS_LOGGED_IN = "isLoggedIn"
        private const val USERNAME = "username"
        private const val USER_EMAIL = "user_email"
        private const val USER_PHOTO = "user_photo"
        private const val TOKEN = "token"
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean(IS_LOGGED_IN, false)
    }

    fun setUsername(username: String) {
        val editor = preferences.edit()
        editor.putString(USERNAME, username)
        editor.apply()
    }

    fun getUsername(): String? {
        return preferences.getString(USERNAME, null)
    }

    fun setUserEmail(email: String) {
        val editor = preferences.edit()
        editor.putString(USER_EMAIL, email)
        editor.apply()
    }

    fun getUserEmail(): String? {
        return preferences.getString(USER_EMAIL, null)
    }

    fun setUserPhoto(photoUrl: String) {
        val editor = preferences.edit()
        editor.putString(USER_PHOTO, photoUrl)
        editor.apply()
    }

    fun getUserPhoto(): String? {
        return preferences.getString(USER_PHOTO, null)
    }

    fun setToken(token: String) {
        val editor = preferences.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return preferences.getString(TOKEN, null)
    }
}
