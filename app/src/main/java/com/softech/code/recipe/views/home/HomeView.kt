package com.softech.code.recipe.views.home

import com.softech.code.recipe.model.Categories
import com.softech.code.recipe.model.Meals

interface HomeView {
    fun showLoading()
    fun hideLoading()
    fun setMeal(meal:List<Meals.Meal>)
    fun setCategory(category:List<Categories.Category>)
    fun setErrorLoading(message:String)
}