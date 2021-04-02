package com.softech.code.recipe.views.home

import com.softech.code.recipe.model.Categories
import com.softech.code.recipe.model.Meals
import com.softech.code.recipe.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter(var view: HomeView) {

    fun getMeals()= CoroutineScope(Dispatchers.IO).launch{
        view.showLoading()
        val mealsCall = Utils.getApi().getMeal()
        mealsCall.enqueue(object : Callback<Meals?> {
            override fun onResponse(call: Call<Meals?>, response: Response<Meals?>) {
                view.hideLoading()
                if (response.isSuccessful && response.body() != null) {
                    view.setMeal(response.body()!!.meals!!)
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
    fun getCategories()= CoroutineScope(Dispatchers.IO).launch{
        view.showLoading()
        val categoriesCall: Call<Categories> = Utils.getApi().getCategories()
        categoriesCall.enqueue(object : Callback<Categories?> {
            override fun onResponse(
                call: Call<Categories?>,
                response: Response<Categories?>
            ) {
                view.hideLoading()
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.categories?.let { view.setCategory(it) }
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