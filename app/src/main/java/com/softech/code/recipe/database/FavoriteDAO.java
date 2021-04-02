package com.softech.code.recipe.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.softech.code.recipe.model.MealFavorite;

import java.util.List;

@Dao
public interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MealFavorite meal);
    
    @Query("SELECT * FROM favorite")
    List<MealFavorite> select();

    @Query("DELETE FROM favorite WHERE strMeal = :name")
    void delete(String name);
    
    @Query("SELECT * FROM favorite WHERE strMeal = :name")
    boolean isFavorite(String name);
}
