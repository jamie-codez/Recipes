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

class ViewPagerHeaderAdapter(
    private val meals: List<Meals.Meal>,
    private val context: Context,
    private var listener: OnItemCLick?
) :
    PagerAdapter() {
    interface OnItemCLick {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemCLick?) {
        this.listener = listener
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
        val mealThumb: ImageView = view.findViewById(R.id.mealThumb)
        val mealName: TextView = view.findViewById(R.id.mealName)
        val strMealThumb: String = meals[position].strMealThumb!!
        Picasso.get().load(strMealThumb).into(mealThumb)
        val strMealName: String? = meals[position].strMeal
        mealName.text = strMealName
        container.addView(view, 0)
        view.setOnClickListener {
            if (position != POSITION_NONE) {
                listener!!.onItemClick(position)
            }
        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }


}
