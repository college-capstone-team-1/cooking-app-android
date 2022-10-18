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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        recipeName: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        val response =
            bookSearchRepository.searchRecipes(startIdx, endIdx, recipeName)
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
    private val _serchPagingResult = MutableStateFlow<PagingData<Recipe>>(PagingData.empty())
    val searchPagingResult: StateFlow<PagingData<Recipe>> = _serchPagingResult.asStateFlow()


    //레시피 이름으로 검색하기 위한 페이징 뷰모델
    fun searchCookingsPaging(RCP_NM: String){
        viewModelScope.launch {
            bookSearchRepository.searchcookingPaging(RCP_NM)
                .cachedIn(viewModelScope)
                .collect {
                    _serchPagingResult.value = it
                }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}