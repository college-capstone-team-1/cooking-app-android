package com.collegecapstoneteam1.cookingapp.data.repository

import androidx.paging.PagingData
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import com.collegecapstoneteam1.cookingapp.data.model.SearchResponse
import com.example.myapplication.test.ServerResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Query

interface RecipeRepository {
    suspend fun searchRecipesList(
        startIdx: Int,
        endIdx: Int,
    ): Response<SearchResponse>

    suspend fun searchRecipes(
        startIdx: Int,
        endIdx: Int,
        recipeName: String,
    ): Response<SearchResponse>

    fun searchcookingPaging(RCP_NM : String): Flow<PagingData<Recipe>>

    suspend fun searchSeverRecipes(
        page: Int,
        size: Int,
    ): Response<ServerResponse>


    // Room
    suspend fun insertRecipe(recipe: Recipe)

    suspend fun deleteRecipe(recipe: Recipe)

    fun getFavoriteRecipes(): Flow<List<Recipe>>
}