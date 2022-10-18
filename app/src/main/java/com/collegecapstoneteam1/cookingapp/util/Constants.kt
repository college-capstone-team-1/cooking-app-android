package com.collegecapstoneteam1.cookingapp.util

import com.collegecapstoneteam1.cookingapp.BuildConfig


object Constants {
    const val BASE_URL = "https://openapi.foodsafetykorea.go.kr/"
    const val API_KEY = BuildConfig.cookAPIKEY
    const val PAGING_SIZE = 15
    const val SEARCH_COOKS_TIME_DELAY = 500L
}