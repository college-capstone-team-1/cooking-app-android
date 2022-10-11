package com.collegecapstoneteam1.cookingapp.ui.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.collegecapstoneteam1.cookingapp.data.api.RetrofitInstance.api
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import retrofit2.HttpException
import java.io.IOException

class RecipePagingSource(
    private val RCP_SEQ: Int
) : PagingSource<Int, Recipe>() {
    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        return try {

            val pageNumber = params.key ?: STARTING_PAGE_INDEX
            val response = api.searchRecipesList(pageNumber, pageNumber+ 20)

            val data = response.body()?.cOOKRCP01?.recipes!!
            val prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber -1
            Log.d("@@@@@@@@@",params.loadSize.toString())
            val nextKey = if(pageNumber > 999){
                null
            } else {
                pageNumber + 21
            }

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey

            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
        const val END_PAGE_INDEX = 20
    }

}