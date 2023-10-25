package com.example.workshopapp

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun createLoginSession(userId: Int) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putInt(KEY_USER_ID, userId)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUserId(): Int {
        return sharedPreferences.getInt(KEY_USER_ID, -1) // -1 as a default value if no user ID is found
    }

    fun logoutUser() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false)
        editor.remove(KEY_USER_ID)
        editor.apply()
    }

    companion object {
        private const val PREF_NAME = "UserSession"
        private const val PRIVATE_MODE = 0

        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USER_ID = "userId"
    }
}

