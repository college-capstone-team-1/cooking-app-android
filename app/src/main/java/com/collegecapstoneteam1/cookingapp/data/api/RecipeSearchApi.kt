package com.collegecapstoneteam1.cookingapp.data.api

import com.collegecapstoneteam1.cookingapp.data.model.FavoriteResponse
import com.collegecapstoneteam1.cookingapp.data.model.SearchResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RecipeSearchApi {
    //Search
    @GET("/api/v1/search/find-only")
    suspend fun searchRecipesList(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<SearchResponse>

    @GET("/api/v1/search/find-only")
    suspend fun searchRecipesList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("name") name: String = "",//이름
        @Query("detail") detail: String = "",//재료 상세
        @Query("part") part: String = "",//음식 구분
        @Query("way") way: String = "",//조리방식
    ): Response<SearchResponse>


    @GET("/api/v1/search/find-only")
    suspend fun searchRecipe(
        @Query("seq") seq: Int,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 1,
    ): Response<SearchResponse>


    //Favorite
    @GET("/api/v1/favorites/give/user")
    suspend fun getUsersFavorite(
        @Query("uid") uid : String,
    ): Response<FavoriteResponse>

    @POST("/api/v1/favorites/take/choose")
    suspend fun favoriteRecipePost(
        @Query("uid") uid : String,
        @Query("recipeSeq") recipeSeq : Int
    ): Response<FavoriteResponse>

    @POST("/api/v1/favorites/delete/choose")
    suspend fun unFavoriteRecipePost(
        @Query("uid") uid : String,
        @Query("recipeSeq") recipeSeq : Int
    ): Response<FavoriteResponse>




}