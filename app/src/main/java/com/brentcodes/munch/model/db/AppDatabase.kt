package com.brentcodes.munch.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}