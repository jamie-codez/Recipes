package com.softech.code.recipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.softech.code.recipe.R
import com.softech.code.recipe.model.Categories
import com.squareup.picasso.Picasso

class RecyclerViewHomeAdapter(
    private val categories: List<Categories.Category>,
    private val context: Context,
    private var listener:OnItemClick?
) :
    RecyclerView.Adapter<RecyclerViewHomeAdapter.RecyclerViewHolder>() {

    fun setOnItemClickListener(listener: OnItemClick?) {
        this.listener = listener
    }

    interface OnItemClick {
        fun onItemClick(position: Int)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerViewHolder {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.item_recycler_category,
            viewGroup, false
        )
        return RecyclerViewHolder(view,listener)
    }

    override fun onBindViewHolder(viewHolder: RecyclerViewHolder, i: Int) {
        val strCategoryThum: String? = categories[i].strCategoryThumb
        Picasso.get().load(strCategoryThum).placeholder(R.drawable.ic_circle)
            .into(viewHolder.categoryThumb)
        val strCategoryName: String? = categories[i].strCategory
        viewHolder.categoryName!!.text = strCategoryName
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class RecyclerViewHolder(itemView: View,listener: OnItemClick?) :
        RecyclerView.ViewHolder(itemView){
        var categoryThumb: ImageView? = itemView.findViewById(R.id.categoryThumb)
        var categoryName: TextView? = itemView.findViewById(R.id.categoryName)
        init {
            itemView.setOnClickListener {
                if (adapterPosition!=RecyclerView.NO_POSITION){
                    listener!!.onItemClick(adapterPosition)
                }
            }
        }
    }

}
