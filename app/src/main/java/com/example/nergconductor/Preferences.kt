package com.example.nergconductor

import android.content.SharedPreferences
import java.util.prefs.Preferences

lateinit var prefs: SharedPreferences

fun SharedPreferences.putAny(name: String, value: Any){
    when(value){
        is String -> edit().putString(name,value).apply()
        is Boolean -> edit().putBoolean(name,value).apply()
    }
}