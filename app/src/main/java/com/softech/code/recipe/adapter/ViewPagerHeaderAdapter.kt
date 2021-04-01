package com.softech.code.recipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.softech.code.recipe.R
import com.softech.code.recipe.model.Meals
import com.squareup.picasso.Picasso

class ViewPagerHeaderAdapter(private val meals: List<Meals.Meal>, private val context: Context) :
    PagerAdapter() {
    fun setOnItemClickListener(clickListener: ClickListener?) {
        Companion.clickListener = clickListener
    }

    override fun getCount(): Int {
        return meals.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view == o
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.item_view_pager_header,
            container,
            false
        )
        val mealThumb = view.findViewById<ImageView>(R.id.mealThumb)
        val mealName = view.findViewById<TextView>(R.id.mealName)
        val strMealThumb: String? = meals[position].strMealThumb
        Picasso.get().load(strMealThumb).into(mealThumb)
        val strMealName: String? = meals[position].strMeal
        mealName.text = strMealName
        view.setOnClickListener { v: View? ->
            clickListener!!.onClick(
                v,
                position
            )
        }
        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    interface ClickListener {
        fun onClick(v: View?, position: Int)
    }

    companion object {
        private var clickListener: ClickListener? = null
    }

}
