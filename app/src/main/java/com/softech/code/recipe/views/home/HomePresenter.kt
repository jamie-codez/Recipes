package com.softech.code.recipe.views.home

import com.softech.code.recipe.model.Categories
import com.softech.code.recipe.model.Meals
import com.softech.code.recipe.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter(var view: HomeView) {
    fun getMeals(){
        view.showLoading()
        val mealsCall = Utils.getApi().getMeal()
        mealsCall.enqueue(object : Callback<Meals?> {
            override fun onResponse(call: Call<Meals?>, response: Response<Meals?>) {
                view.hideLoading()
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.meals?.let { view.setMeal(it) }
                } else {
                    view.setErrorLoading(response.message())
                }
            }

            override fun onFailure(call: Call<Meals?>, t: Throwable) {
                view.hideLoading()
                view.setErrorLoading(t.localizedMessage!!)
            }
        })
    }
    fun getCategories() {
        view.showLoading()
        val categoriesCall: Call<Categories> = Utils.getApi().getCategories()
        categoriesCall.enqueue(object : Callback<Categories?> {
            override fun onResponse(
                call: Call<Categories?>,
                response: Response<Categories?>
            ) {
                view.hideLoading()
                if (response.isSuccessful && response.body() != null) {
                    view.setCategory(response.body()!!.categories)
                } else {
                    view.setErrorLoading(response.message())
                }
            }

            override fun onFailure(call: Call<Categories?>, t: Throwable) {
                view.hideLoading()
                view.setErrorLoading(t.localizedMessage!!)
            }
        })
    }
}