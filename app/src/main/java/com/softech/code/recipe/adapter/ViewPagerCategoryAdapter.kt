package com.softech.code.recipe.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.softech.code.recipe.model.Categories
import com.softech.code.recipe.views.category.CategoryFragment
class ViewPagerCategoryAdapter(
    fm: FragmentManager?,
    private val categories: List<Categories.Category>
) :
    FragmentPagerAdapter(fm!!) {
    override fun getItem(i: Int): Fragment {
        val fragment = CategoryFragment()
        val args = Bundle()
        args.putString("EXTRA_DATA_NAME", categories[i].strCategory)
        args.putString("EXTRA_DATA_DESC", categories[i].strCategoryDescription)
        args.putString("EXTRA_DATA_IMAGE", categories[i].strCategoryThumb)
        fragment.arguments = args
        return fragment
    }

    override fun getCount(): Int {
        return categories.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return categories[position].strCategory
    }

}
