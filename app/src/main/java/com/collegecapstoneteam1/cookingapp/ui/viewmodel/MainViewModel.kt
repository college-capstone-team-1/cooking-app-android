package com.collegecapstoneteam1.cookingapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.collegecapstoneteam1.cookingapp.data.model.SearchResponse
import com.collegecapstoneteam1.cookingapp.data.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val bookSearchRepository: RecipeRepository
) : ViewModel() {
    private val _searchResult = MutableLiveData<SearchResponse>()
    val searchResult: LiveData<SearchResponse> get() = _searchResult
    private var startNum = 1


    fun addNum() {
        startNum += 5
    }

    fun decreaseNum() {
        if (startNum != 1) startNum -= 5
    }

    fun searchRecipes(
        startIdx: Int,
        endIdx: Int,
        recipeName: String,
        recipeDetail: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        val response =
            bookSearchRepository.searchRecipes(startIdx, endIdx, recipeName, recipeDetail)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _searchResult.postValue(body)
            }
        } else {
            Log.d(TAG, "searchBooks: response.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }

    fun searchRecipesList(
    ) = viewModelScope.launch(Dispatchers.IO) {
        val response = bookSearchRepository.searchRecipesList(startNum, startNum + 4)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _searchResult.postValue(body)
            }
        } else {
            Log.d(TAG, "searchBooks: response.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}