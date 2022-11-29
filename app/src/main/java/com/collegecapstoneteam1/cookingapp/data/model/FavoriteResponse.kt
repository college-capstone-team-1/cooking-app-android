package com.collegecapstoneteam1.cookingapp.data.model


import com.google.gson.annotations.SerializedName

data class FavoriteResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("favoriteRecipes")
    val favoriteRecipes: List<FavoriteRecipe>
)