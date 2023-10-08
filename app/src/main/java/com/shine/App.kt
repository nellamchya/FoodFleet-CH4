package com.shine

import android.app.Application
import com.shine.foodfleet.data.local.database.AppDatabase


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}