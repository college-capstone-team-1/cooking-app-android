package com.collegecapstoneteam1.cookingapp.ui.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.collegecapstoneteam1.cookingapp.data.model.FavoriteRecipe
import com.collegecapstoneteam1.cookingapp.data.model.FavoriteResponse
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import com.collegecapstoneteam1.cookingapp.data.model.SearchResponse
import com.collegecapstoneteam1.cookingapp.data.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.await

class MainViewModel(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private val _searchResult = MutableLiveData<SearchResponse>()
    val searchResult: LiveData<SearchResponse> get() = _searchResult

    fun searchRecipes(
        page: Int,
        size: Int,
        name: String = "",//이름
        detail: String = "",//재료 상세
        part: String = "",//음식 구분
        way: String = "",//조리방식
    ) = viewModelScope.launch(Dispatchers.IO) {
        val response =
            recipeRepository.searchRecipesList(page, size, name, detail, part, way)
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
        val response =
            recipeRepository.searchRecipesList(1, 10)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _searchResult.postValue(body)
            }
        } else {
            Log.d(TAG, "searchRecipes: response.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }

    fun getFavoriteBest(
    ) = viewModelScope.launch(Dispatchers.IO) {
        val response =
            recipeRepository.getFavoriteBest()
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


    //레시피 이름으로 검색하기 위한 페이징 뷰모델
    fun searchCookingsPaging(
        name: String = "",//이름
        detail: String = "",//재료 상세
        part: String = "",//음식 구분
        way: String = "",//조리방식
    ) {
        viewModelScope.launch {
            recipeRepository.searchRecipePaging(name, detail, part, way)
                .cachedIn(viewModelScope)
                .collect {
                    _serchPagingResult.value = it
                }
        }
    }


    //favorite
    private val _usersFavorite = MutableLiveData<FavoriteResponse>()
    val usersFavorite: LiveData<FavoriteResponse> get() = _usersFavorite

    fun setUserFavoriteClear(){
        _usersFavorite.postValue(FavoriteResponse(0, listOf()))
    }

    fun getUsersFavorite(uid: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = recipeRepository.getUsersFavorite(uid)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _usersFavorite.postValue(body)
            }
        } else {
            Log.d(TAG, "getUsersFavorite: response.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }

    fun isInFavoite(seq: Int): Boolean {
        usersFavorite.value?.favoriteRecipes?.forEach { if (it.recipeSeq == seq) return true }
        return false
    }

    fun favoriteRecipePost(uid: String, recipeSeq: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = recipeRepository.favoriteRecipePost(uid, recipeSeq)
        if (response.isSuccessful){
            response.body()?.let {
                _usersFavorite.postValue(it)
                Log.d(TAG, "favoriteRecipePost: isSuccess ${it}")
                //getUsersFavorite(uid)
            }
        }else {
            Log.d(TAG, "favoriteRecipePost: response.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }


    fun unFavoriteRecipePost(uid: String, recipeSeq: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = recipeRepository.unFavoriteRecipePost(uid, recipeSeq)
        if (response.isSuccessful){
            response.body()?.let {
                _usersFavorite.postValue(it)
                Log.d(TAG, "unFavoriteRecipePost: isSuccess ${it}")
            }
        }else {
            Log.d(TAG, "unFavoriteRecipePost: response.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }

    // Room
    fun saveRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        recipeRepository.insertRecipe(recipe)
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        recipeRepository.deleteRecipe(recipe)
    }

    var name = ""

    val savedRecipes: StateFlow<List<Recipe>> = recipeRepository.getSavedRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())


    companion object {
        private const val TAG = "MainViewModel"
    }

}