package com.softech.code.recipe.views.detail

import com.softech.code.recipe.model.Meals
import com.softech.code.recipe.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter(var view: DetailView) {
    fun getMealById(mealName: String?) {
        view.showLoading()
        Utils.getApi().getMealByName(mealName)
            .enqueue(object : Callback<Meals?> {
                override fun onResponse(call: Call<Meals?>, response: Response<Meals?>) {
                    view.hideLoading()
                    if (response.isSuccessful && response.body() != null) {
                        view.setMeal(response.body()!!.meals!![0])
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