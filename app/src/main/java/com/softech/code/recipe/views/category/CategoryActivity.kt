package com.softech.code.recipe.views.category

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.softech.code.recipe.R
import com.softech.code.recipe.adapter.ViewPagerCategoryAdapter
import com.softech.code.recipe.databinding.ActivityCategoryBinding
import com.softech.code.recipe.model.Categories

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCategoryBinding
    private lateinit var toolbar:Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = binding.toolbar
        initActionBar()
        initIntent()
    }
    private fun initIntent() {
        val intent = intent
        val categories: List<Categories.Category> = intent.getSerializableExtra("category") as List<Categories.Category>
        val position = intent.getIntExtra("position", 0)
        val adapter = ViewPagerCategoryAdapter(
            supportFragmentManager,
            categories
        )
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.setCurrentItem(position, true)
        adapter.notifyDataSetChanged()
    }
    private fun initActionBar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> onBackPressed()
        }
        return true
    }
}