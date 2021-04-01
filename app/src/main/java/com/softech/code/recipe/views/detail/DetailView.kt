package com.softech.code.recipe.views.detail

import com.softech.code.recipe.model.Categories
import com.softech.code.recipe.model.Meals

interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun setMeal(meal:Meals.Meal)
    fun setErrorLoading(message:String)
}