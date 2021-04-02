package com.softech.code.recipe.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.softech.code.recipe.R
import com.softech.code.recipe.database.FavoriteRepository
import com.softech.code.recipe.model.MealFavorite
import com.softech.code.recipe.model.Meals
import com.softech.code.recipe.views.detail.DetailActivity
import com.squareup.picasso.Picasso

class RecyclerViewMealByCategory(
    private val context: Context,
    private val meals: List<Meals.Meal>,
    private val repository: FavoriteRepository,
    private var listener: OnItemClick?
) :
    RecyclerView.Adapter<RecyclerViewMealByCategory.RecyclerViewHolder>() {
    fun setOnItemClickListener(listener: OnItemClick?) {
        this.listener = listener
    }

    interface OnItemClick {
        fun onClick(position: Int)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerViewHolder {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.item_recycler_meal,
            viewGroup, false
        )
        return RecyclerViewHolder(view,listener)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(viewHolder: RecyclerViewHolder, i: Int) {
        val strMealThumb: String? = meals[i].strMealThumb
        Picasso.get().load(strMealThumb).placeholder(R.drawable.shadow_bottom_to_top)
            .into(viewHolder.mealThumb)
        val strMealName: String? = meals[i].strMeal
        viewHolder.mealName!!.text = strMealName
        if (strMealName?.let { isFavorite(it) } == true) {
            viewHolder.love!!.setImageDrawable(context.resources.getDrawable(R.drawable.ic_favorite))
        } else {
            viewHolder.love!!.setImageDrawable(context.resources.getDrawable(R.drawable.ic_favorite_border))
        }
        viewHolder.itemView.setOnClickListener {
            val mealName: TextView = it.findViewById(R.id.mMealName)
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("detail", mealName.text.toString())
            context.startActivity(intent)
        }
        viewHolder.love!!.setOnClickListener { v: View? ->
            addOrRemoveToFavorite(meals[i])
            if (strMealName?.let { isFavorite(it) } == true) {
                viewHolder.love!!.setImageDrawable(
                    context.resources.getDrawable(R.drawable.ic_favorite)
                )
            } else {
                viewHolder.love!!.setImageDrawable(
                    context.resources.getDrawable(R.drawable.ic_favorite_border)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    class RecyclerViewHolder(itemView: View, onItemClick: OnItemClick?) :
        RecyclerView.ViewHolder(itemView) {
        var mealThumb: ImageView? = itemView.findViewById(R.id.mealThumb)
        var mealName: TextView? = itemView.findViewById(R.id.mMealName)
        var love: ImageView? = itemView.findViewById(R.id.love)
        init {
            itemView.setOnClickListener { 
                if (adapterPosition!=RecyclerView.NO_POSITION){
                    onItemClick?.onClick(adapterPosition)
                }
            }
        }
    }

    private fun addOrRemoveToFavorite(meal: Meals.Meal) {
        if (meal.strMeal?.let { isFavorite(it) } == true) {
            repository.delete(meal.strMeal)
        } else {
            val mealFavorite = MealFavorite("","","")
            mealFavorite.idMeal = meal.idMeal.toString()
            mealFavorite.strMeal = meal.strMeal
            mealFavorite.strMealThumb = meal.strMealThumb
            repository.insert(mealFavorite)
        }
    }

    private fun isFavorite(strMealName: String): Boolean {
        return repository.isFavorite(strMealName)
    }
}
