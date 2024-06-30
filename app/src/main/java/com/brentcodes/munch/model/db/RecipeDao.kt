package com.brentcodes.munch.model.db

import androidx.room.Insert
import androidx.room.Query

interface RecipeDao {
    @Insert
    suspend fun insert(recipe: RecipeEntity)

    @Query("SELECT * FROM saved_recipes")
    suspend fun getAllSavedRecipes(): List<RecipeEntity>
}