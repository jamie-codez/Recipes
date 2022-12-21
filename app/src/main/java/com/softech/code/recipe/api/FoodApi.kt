package com.softech.code.recipe.api

import com.softech.code.recipe.model.Categories
import com.softech.code.recipe.model.Meals
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {
    @GET("latest.php")
    fun getMealE():Call<Meals>
    @GET("random.php")
    fun getMeal():Call<Meals>
    @GET("categories.php")
    fun getCategories():Call<Categories>
    @GET("filter.php")
    fun getMealByCategory(@Query("c") category:String):Call<Meals>
    @GET("search.php")
    fun getMealByName(@Query("s") mealName:String?):Call<Meals>
}