package com.softech.code.recipe.views.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.softech.code.recipe.R
import com.softech.code.recipe.database.FavoriteRepository
import com.softech.code.recipe.databinding.ActivityDetailBinding
import com.softech.code.recipe.model.MealFavorite
import com.softech.code.recipe.model.Meals
import com.softech.code.recipe.utils.Utils
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity(), DetailView {
    private lateinit var repository: FavoriteRepository
    private lateinit var meal: Meals.Meal
    private lateinit var favoriteItem: MenuItem
    private lateinit var strMealName: String
    private lateinit var measures: TextView
    private lateinit var ingredients: TextView
    private lateinit var binding: ActivityDetailBinding

    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository =
            FavoriteRepository(application)
        setupActionBar()
        val intent = intent
        strMealName = intent.getStringExtra("extra_detail").toString()
        measures = binding.measure
        ingredients = binding.ingredient
        val presenter = DetailPresenter(this)
        presenter.getMealById(strMealName)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = resources.getString(R.string.interstitial_ads_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        binding.collapsingToolbar.setContentScrimColor(resources.getColor(R.color.colorWhite))
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.colorPrimary))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.colorWhite))
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupColorActionBarIcon(favoriteItemColor: Drawable) {
        binding.appbar.addOnOffsetChangedListener(OnOffsetChangedListener { _: AppBarLayout?, verticalOffset: Int ->
            if (binding.collapsingToolbar.height + verticalOffset < 2 * ViewCompat.getMinimumHeight(
                    binding.collapsingToolbar
                )
            ) {
                if (binding.toolbar.navigationIcon != null) binding.toolbar.navigationIcon!!
                    .setColorFilter(
                        resources.getColor(R.color.colorPrimary),
                        PorterDuff.Mode.SRC_ATOP
                    )
                favoriteItemColor.mutate().setColorFilter(
                    resources.getColor(R.color.colorPrimary),
                    PorterDuff.Mode.SRC_ATOP
                )
            } else {
                if (binding.toolbar.navigationIcon != null) binding.toolbar.navigationIcon!!
                    .setColorFilter(
                        resources.getColor(R.color.colorWhite),
                        PorterDuff.Mode.SRC_ATOP
                    )
                favoriteItemColor.mutate().setColorFilter(
                    resources.getColor(R.color.colorWhite),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        favoriteItem = menu.findItem(R.id.favorite)
        setFavoriteItem()
        val favoriteItemColor = favoriteItem.getIcon()
        setupColorActionBarIcon(favoriteItemColor)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.favorite -> {
                addOrRemoveToFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addOrRemoveToFavorite() {
        if (isFavorite()) {
            repository.delete(meal.strMeal)
        } else {
            val mealFavorite = MealFavorite("", "", "")
            mealFavorite.idMeal = meal.idMeal.toString()
            mealFavorite.strMeal = meal.strMeal
            mealFavorite.strMealThumb = meal.strMealThumb
            repository.insert(mealFavorite)
        }
        setFavoriteItem()
    }

    private fun isFavorite(): Boolean {
        return repository.isFavorite(strMealName)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setFavoriteItem() {
        if (isFavorite()) {
            favoriteItem.icon = resources.getDrawable(R.drawable.ic_favorite)
        } else {
            favoriteItem.icon = resources.getDrawable(R.drawable.ic_favorite_border)
        }
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun setMeal(meal: Meals.Meal) {
        this.meal = meal
        Picasso.get().load(meal.strMealThumb).into(binding.mealThumb)
        binding.collapsingToolbar.title = meal.strMeal
        binding.category.text = meal.strCategory
        binding.country.text = meal.strDrinkAlternate
        binding.instructions.text = meal.strInstructions
        setupActionBar()

        if (meal.strIngredient1!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient1}"""
            )
        }
        if (meal.strIngredient2!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient2}"""
            )
        }
        if (meal.strIngredient3!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient3!!}"""
            )
        }
        if (meal.strIngredient4!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient4}"""
            )
        }
        if (meal.strIngredient5!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient5}"""
            )
        }
        if (meal.strIngredient6!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient6}"""
            )
        }
        if (meal.strIngredient7!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient7}"""
            )
        }
        if (meal.strIngredient8!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient8}"""
            )
        }
        if (meal.strIngredient9!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient9}"""
            )
        }
        if (meal.strIngredient10!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient10}"""
            )
        }
        if (meal.strIngredient11!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient11}"""
            )
        }
        if (meal.strIngredient12!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient12}"""
            )
        }
        if (meal.strIngredient13!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient13}"""
            )
        }
        if (meal.strIngredient14!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient14}"""
            )
        }
        if (meal.strIngredient15!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient15}"""
            )
        }
        if (meal.strIngredient16!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient16}"""
            )
        }
        if (meal.strIngredient17!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient17}"""
            )
        }
        if (meal.strIngredient18!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient18}"""
            )
        }
        if (meal.strIngredient19!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient19}"""
            )
        }
        if (meal.strIngredient20!!.isNotEmpty()) {
            ingredients.append(
                """
 • ${meal.strIngredient20}"""
            )
        }
        if (meal.strMeasure1!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure1!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure1}"""
            )
        }
        if (meal.strMeasure2!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure2!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure2}"""
            )
        }
        if (meal.strMeasure3!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure3!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure3}"""
            )
        }
        if (meal.strMeasure4!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure4!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure4}"""
            )
        }
        if (meal.strMeasure5!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure5!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure5}"""
            )
        }
        if (meal.strMeasure6!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure6!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure6}"""
            )
        }
        if (meal.strMeasure7!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure7!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure7}"""
            )
        }
        if (meal.strMeasure8!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure8!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure8}"""
            )
        }
        if (meal.strMeasure9!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure9!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure9}"""
            )
        }
        if (meal.strMeasure10!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure10!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure10}"""
            )
        }
        if (meal.strMeasure11!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure11!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure11}"""
            )
        }
        if (meal.strMeasure12!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure12!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure12}"""
            )
        }
        if (meal.strMeasure13!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure13!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure13}"""
            )
        }
        if (meal.strMeasure14!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure14!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure14}"""
            )
        }
        if (meal.strMeasure15!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure15!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure15}"""
            )
        }
        if (meal.strMeasure16!!.isNotEmpty() && !Character.isWhitespace(meal.strMeasure16!![0])) {
            measures.append(": ${meal.strMeasure16}")
        }
        if (meal.strMeasure17!!.isNotEmpty() && !Character.isWhitespace(meal.strMeasure17!![0])) {
            measures.append(": ${meal.strMeasure17}")
        }
        if (meal.strMeasure18!!.isNotEmpty() && !Character.isWhitespace( meal.strMeasure18!![0])) {
            measures.append(": ${meal.strMeasure18}")
        }
        if (meal.strMeasure19!!.isNotEmpty() && !Character.isWhitespace(meal.strMeasure19!![0])) {
            measures.append(": ${meal.strMeasure19}")
        }
        if (meal.strMeasure20!!.isNotEmpty() && !Character.isWhitespace(
                meal.strMeasure20!![0]
            )
        ) {
            measures.append(
                """
 : ${meal.strMeasure20}"""
            )}
        binding.youtube.setOnClickListener {
            val intentYoutube = Intent(Intent.ACTION_VIEW)
            intentYoutube.data = Uri.parse(meal.strYoutube)
            startActivity(intentYoutube)
        }
        binding.source.setOnClickListener {
            val intentSource = Intent(Intent.ACTION_VIEW)
            intentSource.data = Uri.parse(meal.strSource)
            startActivity(intentSource)
        }
    }

    override fun setErrorLoading(message: String) {
        Utils.showAlertDialogMessage(this, "Error", message)
    }

    override fun onBackPressed() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            finish()
            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                val handler = Handler()
                handler.postDelayed({ supportFinishAfterTransition() }, 200)
            }
        }
        super.onBackPressed()
    }
}