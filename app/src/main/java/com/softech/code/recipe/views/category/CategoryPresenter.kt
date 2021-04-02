package com.softech.code.recipe.views.category

import com.softech.code.recipe.model.Meals
import com.softech.code.recipe.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryPresenter(val view: CategoryView) {

    fun getMealByCategory(category: String?)= CoroutineScope(Dispatchers.IO).launch{
        view.showLoading()
        val mealsCall: Call<Meals>? = category?.let { Utils.getApi().getMealByCategory(it) }
        mealsCall!!.enqueue(object : Callback<Meals?> {
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
}