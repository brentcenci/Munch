package com.brentcodes.munch.model.db

class RecipeRepository(private val recipeDao: RecipeDao) {
    suspend fun saveRecipe(id: String) {
        recipeDao.insert(RecipeEntity(id))
    }

    suspend fun getSavedRecipes(): List<RecipeEntity> {
        return recipeDao.getAllSavedRecipes()
    }
}