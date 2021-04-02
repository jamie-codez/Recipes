package com.softech.code.recipe.database;

import android.app.Application;


import com.softech.code.recipe.model.MealFavorite;

import java.util.List;

public class FavoriteRepository {

    private final FavoriteDAO favoriteDAO;

    public FavoriteRepository(Application application) {
        FavoriteDB database = FavoriteDB.getDatabase(application);
        favoriteDAO = database.favoriteDAO();
    }
    
    public void insert(MealFavorite meal) {
        favoriteDAO.insert(meal);
    }
    
    public void delete(String name) {
        favoriteDAO.delete(name);
    }
    
    public List<MealFavorite> select() {
        return favoriteDAO.select();
    }
    
    public boolean isFavorite(String name) {
        return favoriteDAO.isFavorite(name);
    }
}
