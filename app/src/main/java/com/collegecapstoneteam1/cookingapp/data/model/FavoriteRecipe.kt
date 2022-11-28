package com.collegecapstoneteam1.cookingapp.data.model


import com.google.gson.annotations.SerializedName

data class FavoriteRecipe(
    @SerializedName("id")
    val id: Int,
    @SerializedName("recipe_seq")
    val recipeSeq: Int,
    @SerializedName("user_email")
    val userEmail: String
)