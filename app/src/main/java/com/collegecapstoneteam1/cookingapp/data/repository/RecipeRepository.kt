package com.collegecapstoneteam1.cookingapp.data.repository

import androidx.paging.PagingData
import com.collegecapstoneteam1.cookingapp.data.model.FavoriteResponse
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import com.collegecapstoneteam1.cookingapp.data.model.SearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Query

interface RecipeRepository {

    //Search Api
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
        sort: String
    ): Response<SearchResponse>

    suspend fun searchRecipe(
        seq: Int,
    ): Response<SearchResponse>

    fun searchRecipePaging(
        name: String = "",//이름
        detail: String = "",//재료 상세
        part: String = "",//음식 구분
        way: String = "",//조리방식
        sort: String = "d"
    ): Flow<PagingData<Recipe>>

    //Favorite Api
    suspend fun getUsersFavorite(
        uid: String,
    ): Response<FavoriteResponse>

    suspend fun favoriteRecipePost(
        uid: String,
        recipeSeq : Int
    ): Response<FavoriteResponse>

    suspend fun unFavoriteRecipePost(
        uid: String,
        recipeSeq : Int
    ): Response<FavoriteResponse>

    suspend fun getFavoriteBest():Response<SearchResponse>

    // Room
    suspend fun insertRecipe(recipe: Recipe)

    suspend fun deleteRecipe(recipe: Recipe)

    fun getSavedRecipes(): Flow<List<Recipe>>

    fun getSavedRecipesWithName(name: String): Flow<List<Recipe>>
}