package com.brentcodes.munch.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Upsert
    suspend fun insert(recipe: RecipeEntity)

    @Query("SELECT * FROM saved_recipes")
    suspend fun getAllSavedRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM saved_recipes")
    fun getAllSavedRecipesFlow(): Flow<List<RecipeEntity>>

    @Delete
    suspend fun delete(recipe: RecipeEntity)

    @Query("DELETE FROM saved_recipes")
    suspend fun deleteAll()
}