package com.collegecapstoneteam1.cookingapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.collegecapstoneteam1.cookingapp.data.api.RetrofitInstance
import com.collegecapstoneteam1.cookingapp.data.db.RecipeDatabase
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import com.collegecapstoneteam1.cookingapp.data.model.SearchResponse
import com.collegecapstoneteam1.cookingapp.ui.paging.RecipePagingSource
import com.collegecapstoneteam1.cookingapp.util.Constants.PAGING_SIZE
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RecipeRepositoryImpl(private val db: RecipeDatabase) : RecipeRepository {
    override suspend fun searchRecipesList(
        page: Int,
        size: Int,
    ): Response<SearchResponse> {
        return RetrofitInstance.api.searchRecipesList(page, size)
    }

    override suspend fun searchRecipesList(
        page: Int,
        size: Int,
        name: String,
        detail: String,
        part: String,
        way: String,
    ): Response<SearchResponse> {
        return RetrofitInstance.api.searchRecipesList(page, size, name, detail, part, way)
    }

    override suspend fun searchRecipe(seq: Int): Response<SearchResponse> {
        return RetrofitInstance.api.searchRecipe(seq)
    }


    override fun searchRecipePaging(
        name: String,
        detail: String,
        part: String,
        way: String,
    ): Flow<PagingData<Recipe>> {
        val pagingSourceFactory = { RecipePagingSource(name, detail, part, way) }

        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                maxSize = PAGING_SIZE * 3
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    // Room

    override suspend fun insertRecipe(recipe: Recipe) {
        db.recipeDao().insertRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        db.recipeDao().deleteRecipe(recipe)
    }

    override fun getFavoriteRecipes(): Flow<List<Recipe>> {
        return db.recipeDao().getFavoriteRecipes()
    }

    override fun getFavoriteRecipesWithName(name: String): Flow<List<Recipe>> {
        return db.recipeDao().getFavoriteRecipesWithName(name)
    }


}