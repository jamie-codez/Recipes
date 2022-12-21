package com.softech.code.recipe.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Meals(
    @SerializedName("meals")
    @Expose
    var meals: List<Meal>?
) {
    data class Meal(
        @SerializedName("idMeal")
        @Expose
        var idMeal: String?,

        @SerializedName("strMeal")
        @Expose
        var strMeal: String?,

        @SerializedName("strDrinkAlternate")
        @Expose
        var strDrinkAlternate: String?,

        @SerializedName("strCategory")
        @Expose
        var strCategory: String?,

        @SerializedName("strArea")
        @Expose
        var strArea: String?,

        @SerializedName("strInstructions")
        @Expose
        var strInstructions: String?,

        @SerializedName("strMealThumb")
        @Expose
        var strMealThumb: String?,

        @SerializedName("strTags")
        @Expose
        var strTags: String?,

        @SerializedName("strYoutube")
        @Expose
        var strYoutube: String?,

        @SerializedName("strIngredient1")
        @Expose
        var strIngredient1: String?,

        @SerializedName("strIngredient2")
        @Expose
        var strIngredient2: String?,

        @SerializedName("strIngredient3")
        @Expose
        var strIngredient3: String?,

        @SerializedName("strIngredient4")
        @Expose
        var strIngredient4: String?,

        @SerializedName("strIngredient5")
        @Expose
        var strIngredient5: String?,

        @SerializedName("strIngredient6")
        @Expose
        var strIngredient6: String?,

        @SerializedName("strIngredient7")
        @Expose
        var strIngredient7: String?,

        @SerializedName("strIngredient8")
        @Expose
        var strIngredient8: String?,

        @SerializedName("strIngredient9")
        @Expose
        var strIngredient9: String?,

        @SerializedName("strIngredient10")
        @Expose
        var strIngredient10: String?,

        @SerializedName("strIngredient11")
        @Expose
        var strIngredient11: String?,

        @SerializedName("strIngredient12")
        @Expose
        var strIngredient12: String?,

        @SerializedName("strIngredient13")
        @Expose
        var strIngredient13: String?,

        @SerializedName("strIngredient14")
        @Expose
        var strIngredient14: String?,

        @SerializedName("strIngredient15")
        @Expose
        var strIngredient15: String?,

        @SerializedName("strIngredient16")
        @Expose
        var strIngredient16: String?,

        @SerializedName("strIngredient17")
        @Expose
        var strIngredient17: String?,

        @SerializedName("strIngredient18")
        @Expose
        var strIngredient18: String?,

        @SerializedName("strIngredient19")
        @Expose
        var strIngredient19: String?,

        @SerializedName("strIngredient20")
        @Expose
        var strIngredient20: String?,

        @SerializedName("strMeasure1")
        @Expose
        var strMeasure1: String?,

        @SerializedName("strMeasure2")
        @Expose
        var strMeasure2: String?,

        @SerializedName("strMeasure3")
        @Expose
        var strMeasure3: String?,

        @SerializedName("strMeasure4")
        @Expose
        var strMeasure4: String?,

        @SerializedName("strMeasure5")
        @Expose
        var strMeasure5: String?,

        @SerializedName("strMeasure6")
        @Expose
        var strMeasure6: String?,

        @SerializedName("strMeasure7")
        @Expose
        var strMeasure7: String?,

        @SerializedName("strMeasure8")
        @Expose
        var strMeasure8: String?,
        @SerializedName("strMeasure9")
        @Expose
        var strMeasure9: String?,

        @SerializedName("strMeasure10")
        @Expose
        var strMeasure10: String?,

        @SerializedName("strMeasure11")
        @Expose
        var strMeasure11: String?,

        @SerializedName("strMeasure12")
        @Expose
        var strMeasure12: String?,

        @SerializedName("strMeasure13")
        @Expose
        var strMeasure13: String?,

        @SerializedName("strMeasure14")
        @Expose
        var strMeasure14: String?,

        @SerializedName("strMeasure15")
        @Expose
        var strMeasure15: String?,

        @SerializedName("strMeasure16")
        @Expose
        var strMeasure16: String?,

        @SerializedName("strMeasure17")
        @Expose
        var strMeasure17: String?,

        @SerializedName("strMeasure18")
        @Expose
        var strMeasure18: String?,

        @SerializedName("strMeasure19")
        @Expose
        var strMeasure19: String?,

        @SerializedName("strMeasure20")
        @Expose
        var strMeasure20: String?,

        @SerializedName("strSource")
        @Expose
        var strSource: String?,

        @SerializedName("dateModified")
        @Expose
        var dateModified: Any? = null
    )
}
