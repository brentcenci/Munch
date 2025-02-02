package com.brentcodes.munch

import android.app.Application
import androidx.room.Room
import com.brentcodes.munch.model.db.AppDatabase

class MyApp : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "recipe_db"
        ).build()
    }
}