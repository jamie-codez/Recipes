package com.softech.code.recipe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.softech.code.recipe.model.MealFavorite

@Database(entities = [MealFavorite::class], version = 1, exportSchema = false)
abstract class FavoriteDB : RoomDatabase() {
    abstract fun favoriteDAO(): FavoriteDAO?

    companion object {
        private var instance: FavoriteDB? = null

        @JvmStatic
        fun getDatabase(context: Context?): FavoriteDB? {
            if (instance == null) {
                synchronized(FavoriteDB::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context!!,
                            FavoriteDB::class.java, "favorite.db"
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return instance
        }
    }
}