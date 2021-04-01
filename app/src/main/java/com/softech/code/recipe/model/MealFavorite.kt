package com.softech.code.recipe.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class MealFavorite(@PrimaryKey var idMeal: String, var strMeal: String?, var strMealThumb: String?)