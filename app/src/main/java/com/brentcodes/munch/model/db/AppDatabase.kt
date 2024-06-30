package com.brentcodes.munch.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brentcodes.munch.ui.Screen

@Database(entities = [RecipeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}