package com.collegecapstoneteam1.cookingapp.data.repository

import com.collegecapstoneteam1.cookingapp.data.model.SearchResponse
import retrofit2.Response

interface RecipeRepository {
    suspend fun searchRecipesList(
        startIdx: Int,
        endIdx: Int,
    ): Response<SearchResponse>

    suspend fun searchRecipes(
        startIdx: Int,
        endIdx: Int,
        recipeName: String,
        recipeDetail: String
    ): Response<SearchResponse>


}