package com.softech.code.recipe.views.category

import com.softech.code.recipe.model.Meals
import com.softech.code.recipe.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryPresenter(val view: CategoryView) {

    fun getMealByCategory(category: String?) {
        view.showLoading()
        val mealsCall: Call<Meals>? = category?.let { Utils.getApi().getMealByCategory(it) }
        mealsCall!!.enqueue(object : Callback<Meals?> {
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
}