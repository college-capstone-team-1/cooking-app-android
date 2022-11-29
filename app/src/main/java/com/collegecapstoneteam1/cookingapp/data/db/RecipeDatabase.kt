package com.collegecapstoneteam1.cookingapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.collegecapstoneteam1.cookingapp.data.model.Recipe

@Database(
    entities = [Recipe::class],
    version = 1,
    exportSchema = false
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        private fun buildDatabase(context: Context): RecipeDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                RecipeDatabase::class.java,
                "Saved-recipes-test"
            ).build()

        fun getInstance(context: Context): RecipeDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
    }
}