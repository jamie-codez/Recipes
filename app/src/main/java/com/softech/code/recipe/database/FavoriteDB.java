package com.softech.code.recipe.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.softech.code.recipe.model.MealFavorite;


@Database(entities = {MealFavorite.class}, version = 1, exportSchema = false)
public abstract class FavoriteDB extends RoomDatabase {
    public abstract FavoriteDAO favoriteDAO();

    private static FavoriteDB INSTANCE;

    public static FavoriteDB getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (FavoriteDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, FavoriteDB.class, "favorite.db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    
}
