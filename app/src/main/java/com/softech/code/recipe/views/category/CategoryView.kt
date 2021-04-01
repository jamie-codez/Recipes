package com.softech.code.recipe.views.category

import com.softech.code.recipe.model.Categories
import com.softech.code.recipe.model.Meals

interface CategoryView {
    fun showLoading()
    fun hideLoading()
    fun setMeal(meals:List<Meals.Meal>)
    fun setCategory(category:List<Categories.Category>)
    fun setErrorLoading(message:String)
}