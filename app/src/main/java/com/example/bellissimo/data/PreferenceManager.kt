package com.example.bellissimo.data

import android.content.Context
import android.content.Context.MODE_PRIVATE

class PreferenceManager(context: Context) {
    val prefs = context.getSharedPreferences("Pref",MODE_PRIVATE)

    fun getLanguage() = prefs.getString("lang","uz")

    fun saveLanguage(code: String){
        prefs.edit().putString("lang",code).apply()
    }
}