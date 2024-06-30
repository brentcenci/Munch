package com.brentcodes.munch.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "saved_recipes")
data class RecipeEntity(
    @PrimaryKey val id: String
)