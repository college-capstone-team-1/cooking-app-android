package com.collegecapstoneteam1.cookingapp.data.db

import androidx.room.*
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipes Order By rcpSeq")
    fun getFavoriteRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE rcpNm = :name")
    fun getFavoriteRecipesWithName(name: String): Flow<List<Recipe>>
}