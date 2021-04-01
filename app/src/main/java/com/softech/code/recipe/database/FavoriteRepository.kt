package com.softech.code.recipe.database

import android.app.Application
import com.softech.code.recipe.model.MealFavorite

class FavoriteRepository(application: Application) {
    private var favoriteDAO: FavoriteDAO? = null

    init {
        val database: FavoriteDB? = FavoriteDB.getDatabase(application)
        favoriteDAO = database!!.favoriteDAO()
    }

    fun insert(meal: MealFavorite?) {
        favoriteDAO!!.insert(meal)
    }

    fun delete(name: String?) {
        favoriteDAO!!.delete(name)
    }

    fun select(): List<MealFavorite?>? {
        return favoriteDAO!!.select()
    }

    fun isFavorite(name: String?): Boolean {
        return favoriteDAO!!.isFavorite(name)
    }
}