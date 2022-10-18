package com.collegecapstoneteam1.cookingapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.collegecapstoneteam1.cookingapp.data.api.RetrofitInstance
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import com.collegecapstoneteam1.cookingapp.data.model.SearchResponse
import com.collegecapstoneteam1.cookingapp.ui.paging.RecipePagingSource
import com.collegecapstoneteam1.cookingapp.util.Constants.PAGING_SIZE
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RecipeRepositoryImpl : RecipeRepository {
    override suspend fun searchRecipesList(startIdx: Int, endIdx: Int): Response<SearchResponse> {
        return RetrofitInstance.api.searchRecipesList(startIdx, endIdx)
    }

    override suspend fun searchRecipes(startIdx: Int, endIdx: Int, recipeName: String): Response<SearchResponse> {
        return RetrofitInstance.api.searchRecipesList(startIdx, endIdx, recipeName)
    }

    override fun searchcookingPaging(RCP_NM: String): Flow<PagingData<Recipe>> {
        val pagingSourceFactory = { RecipePagingSource(RCP_NM) }

        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                maxSize = PAGING_SIZE * 3
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


}