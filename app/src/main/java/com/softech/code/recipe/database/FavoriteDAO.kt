package com.softech.code.recipe.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softech.code.recipe.model.MealFavorite

@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(meal: MealFavorite?): Long

    @Query("SELECT * FROM favorite")
    fun select(): List<MealFavorite?>?

    @Query("DELETE FROM favorite WHERE strMeal = :name")
    fun delete(name: String?)

    @Query("SELECT * FROM favorite WHERE strMeal = :name")
    fun isFavorite(name: String?): Boolean
}