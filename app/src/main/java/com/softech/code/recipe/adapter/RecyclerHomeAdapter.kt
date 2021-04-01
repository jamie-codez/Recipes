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
    private val context: Context
) :
    RecyclerView.Adapter<RecyclerViewHomeAdapter.RecyclerViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerViewHolder {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.item_recycler_category,
            viewGroup, false
        )
        return RecyclerViewHolder(view)
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

    class RecyclerViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var categoryThumb: ImageView? = itemView.findViewById(R.id.categoryThumb)

        var categoryName: TextView? = itemView.findViewById(R.id.categoryName)
        override fun onClick(v: View) {
            clickListener!!.onClick(v, adapterPosition)
            // todo :fix all these onclicks
            // todo :viewpager not working
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    fun setOnItemClickListener(clickListener: ClickListener?) {
        Companion.clickListener = clickListener
    }

    interface ClickListener {
        fun onClick(view: View?, position: Int)
    }

    companion object {
        private var clickListener: ClickListener? = null
    }

}
