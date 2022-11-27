package com.collegecapstoneteam1.cookingapp.data.repository

import androidx.paging.PagingData
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import com.collegecapstoneteam1.cookingapp.data.model.SearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RecipeRepository {
    suspend fun searchRecipesList(
        page: Int,
        size: Int,
    ): Response<SearchResponse>

    suspend fun searchRecipesList(
        page: Int,
        size: Int,
        name: String,//이름
        detail: String,//재료 상세
        part: String,//음식 구분
        way: String,//조리방식
    ): Response<SearchResponse>

    suspend fun searchRecipe(
        seq: Int,
    ): Response<SearchResponse>

    fun searchRecipePaging(
        name: String = "",//이름
        detail: String = "",//재료 상세
        part: String = "",//음식 구분
        way: String = "",//조리방식
    ): Flow<PagingData<Recipe>>

    // Room
    suspend fun insertRecipe(recipe: Recipe)

    suspend fun deleteRecipe(recipe: Recipe)

    fun getSavedRecipes(): Flow<List<Recipe>>

    fun getSavedRecipesWithName(name: String): Flow<List<Recipe>>
}