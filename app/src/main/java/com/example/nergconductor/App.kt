package com.example.nergconductor

import android.app.Application
import android.content.Context
import java.util.prefs.Preferences

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        prefs = getSharedPreferences("CONDUCTOR",Context.MODE_PRIVATE)
    }
}