package com.collegecapstoneteam1.cookingapp.data.api

import com.example.myapplication.test.ServerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeServerApi {
    @GET("/api/v1")
    suspend fun searchSeverRecipes(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<ServerResponse>
}