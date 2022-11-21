package com.collegecapstoneteam1.cookingapp.data.model


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val meta: Meta,
    @SerializedName("values")
    val recipes: List<Recipe>
)