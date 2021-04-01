package com.softech.code.recipe.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.softech.code.recipe.api.FoodApi
import com.softech.code.recipe.api.FoodClient

object Utils {
    @JvmStatic
    fun getApi(): FoodApi {
        return FoodClient.getFoodClient().create(FoodApi::class.java)
    }
    fun showAlertDialogMessage(context: Context,title:String,message:String):AlertDialog{
        val alertDialog = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .show()
        if (alertDialog.isShowing){
            alertDialog.cancel()
        }
        return alertDialog
    }
}