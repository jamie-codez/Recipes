package com.softech.code.recipe.views.favorite

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.softech.code.recipe.adapter.RecyclerViewMealFavorite
import com.softech.code.recipe.database.FavoriteRepository
import com.softech.code.recipe.databinding.ActivityFavoriteBinding
import com.softech.code.recipe.model.MealFavorite

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFavoriteBinding
    private lateinit var repository: FavoriteRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        repository = FavoriteRepository(application)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.clipToPadding = false

    }
    private fun getFavoriteList() {
        repository = FavoriteRepository(application)
        val adapter = RecyclerViewMealFavorite(
            this,
            repository.select() as List<MealFavorite>, repository
        )
        binding.recyclerView.adapter = adapter
//        adapter.setOnItemClickListener { view, position ->
//            val strMealName: TextView = view.findViewById(R.id.mealName)
//            val intent = Intent(this, DetailActivity::class.java)
//            intent.putExtra(EXTRA_DETAIL, strMealName.text.toString())
//            startActivity(intent)
//        }
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