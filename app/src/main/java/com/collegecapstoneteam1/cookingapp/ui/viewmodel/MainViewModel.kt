package com.collegecapstoneteam1.cookingapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import com.collegecapstoneteam1.cookingapp.data.model.SearchResponse
import com.collegecapstoneteam1.cookingapp.data.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val recipeRepository: RecipeRepository
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
        recipeName: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        val response =
            recipeRepository.searchRecipes(startIdx, endIdx, recipeName)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _searchResult.postValue(body)
            }
        } else {
            Log.d(TAG, "searchRecipes: response.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }

    fun searchRecipesList(
    ) = viewModelScope.launch(Dispatchers.IO) {
        val response = recipeRepository.searchRecipesList(startNum, startNum + 4)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _searchResult.postValue(body)
            }
        } else {
            Log.d(TAG, "searchRecipes: response.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }

    private val _serchPagingResult = MutableStateFlow<PagingData<Recipe>>(PagingData.empty())
    val searchPagingResult: StateFlow<PagingData<Recipe>> = _serchPagingResult.asStateFlow()


    fun searchServerRecipes(
        page: Int,
        size: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        val response =
            recipeRepository.searchSeverRecipes(page, size)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                Log.d(TAG, "searchServerRecipes: ${body}")
            }
        } else {
            Log.d(TAG, "searchRecipes: response.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }


    //레시피 이름으로 검색하기 위한 페이징 뷰모델
    fun searchCookingsPaging(RCP_NM: String) {
        viewModelScope.launch {
            recipeRepository.searchcookingPaging(RCP_NM)
                .cachedIn(viewModelScope)
                .collect {
                    _serchPagingResult.value = it
                }
        }
    }


    // Room
    fun saveRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        recipeRepository.insertRecipe(recipe)
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        recipeRepository.deleteRecipe(recipe)
    }

    val favoriteRecipes: StateFlow<List<Recipe>> = recipeRepository.getFavoriteRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())


    companion object {
        private const val TAG = "MainViewModel"
    }

}