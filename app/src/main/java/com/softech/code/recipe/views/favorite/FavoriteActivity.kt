package com.softech.code.recipe.views.favorite

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.softech.code.recipe.adapter.RecyclerViewMealFavorite
import com.softech.code.recipe.database.FavoriteRepository
import com.softech.code.recipe.databinding.ActivityFavoriteBinding
import com.softech.code.recipe.views.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFavoriteBinding
    private lateinit var repository: FavoriteRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val window = window
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setSupportActionBar(binding.toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        repository =
            FavoriteRepository(application)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.clipToPadding = false

    }
    private fun getFavoriteList() {
        repository =
            FavoriteRepository(application)
        val adapter = RecyclerViewMealFavorite(
            this,
            repository.select()!!,
            repository,
            null
        )
        binding.recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object:RecyclerViewMealFavorite.OnItemClick{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
            intent.putExtra("detail", repository.select()!![position]!!.strMeal)
            startActivity(intent)
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        getFavoriteList()
    }

}