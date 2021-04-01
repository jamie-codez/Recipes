package com.softech.code.recipe.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Categories(
    @SerializedName("categories")
    @Expose
    var categories: List<Category>
) : Serializable {

    data class Category(
        @SerializedName("idCategory")
        @Expose
        var idCategory: String?,
        @SerializedName("strCategory")
        @Expose
        var strCategory: String?,
        @SerializedName("strCategoryThumb")
        @Expose
        var strCategoryThumb: String?,
        @SerializedName("strCategoryDescription")
        @Expose
        var strCategoryDescription: String?
    ) : Serializable
}