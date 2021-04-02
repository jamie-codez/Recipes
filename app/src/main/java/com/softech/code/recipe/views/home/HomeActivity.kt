package com.softech.code.recipe.views.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.softech.code.recipe.R
import com.softech.code.recipe.adapter.RecyclerViewHomeAdapter
import com.softech.code.recipe.adapter.ViewPagerHeaderAdapter
import com.softech.code.recipe.databinding.ActivityHomeBinding
import com.softech.code.recipe.model.Categories
import com.softech.code.recipe.model.Meals
import com.softech.code.recipe.utils.Utils
import com.softech.code.recipe.views.category.CategoryActivity
import com.softech.code.recipe.views.detail.DetailActivity
import com.softech.code.recipe.views.favorite.FavoriteActivity
import java.io.Serializable

class HomeActivity : AppCompatActivity(), HomeView {

    val CATEGORY_EXTRA = "category"
    val EXTRA_POSITION = "position"
    val EXTRA_DETAIL = "detail"
    lateinit var binding: ActivityHomeBinding
    lateinit var presenter: HomePresenter
    private var mInterstitialAd: InterstitialAd?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.favorite.setOnClickListener {
            startActivity(Intent(this,FavoriteActivity::class.java))
        }
        presenter = HomePresenter(this);
        presenter.getMeals()
        presenter.getCategories()
        MobileAds.initialize(this.applicationContext, resources.getString(R.string.banner_ads_id))

        val adRequest: AdRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("TAG", adError.message)
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("TAG", "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                    mInterstitialAd!!.show(this@HomeActivity)

                }
            })
    }


    override fun showLoading() {
        binding.shimmerMeal.shimmerViewContainer2.visibility = VISIBLE
        binding.shimmerCategory.shimmerCategoryContainer.visibility = VISIBLE
    }

    override fun hideLoading() {
        binding.shimmerMeal.shimmerViewContainer2.visibility = GONE
        binding.shimmerCategory.shimmerCategoryContainer.visibility = GONE
    }

    override fun setMeal(meal: List<Meals.Meal>) {
        val headerAdapter = ViewPagerHeaderAdapter(meal, this, null)
        binding.viewPagerHeader.adapter = headerAdapter
        binding.viewPagerHeader.setPadding(20, 0, 20, 0)
        headerAdapter.notifyDataSetChanged()

        headerAdapter.setOnItemClickListener(object : ViewPagerHeaderAdapter.OnItemCLick {
            override fun onItemClick(position: Int) {
                val mealName = meal[position].strMeal
                val intent = Intent(applicationContext, DetailActivity::class.java)
                intent.putExtra("detail", mealName)
                startActivity(intent)
            }

        })
    }

    override fun setCategory(category: List<Categories.Category>) {
        val homeAdapter = RecyclerViewHomeAdapter(category, this, null)
        binding.recyclerCategory.adapter = homeAdapter
        val layoutManager = GridLayoutManager(
            this, 3,
            GridLayoutManager.VERTICAL, false
        )
        binding.recyclerCategory.layoutManager = layoutManager
        binding.recyclerCategory.isNestedScrollingEnabled = true
        homeAdapter.notifyDataSetChanged()
        homeAdapter.setOnItemClickListener(object : RecyclerViewHomeAdapter.OnItemClick {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@HomeActivity, CategoryActivity::class.java)
                intent.putExtra(CATEGORY_EXTRA, category as Serializable)
                intent.putExtra(EXTRA_POSITION, position)
                startActivity(intent)

            }
        })
    }

    override fun setErrorLoading(message: String) {
        Utils.showAlertDialogMessage(this, "Title", message)
    }

    override fun onBackPressed() {
        mInterstitialAd!!.onPaidEventListener = object : AdListener(), OnPaidEventListener {
            override fun onAdClosed() {
                super.onAdClosed()
                val handler = Handler()
                handler.postDelayed({ supportFinishAfterTransition() }, 200)
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                mInterstitialAd!!.show(this@HomeActivity)
            }

            override fun onAdClicked() {
                super.onAdClicked()
                Toast.makeText(this@HomeActivity, "Add clicked", Toast.LENGTH_SHORT).show()
            }

            override fun onPaidEvent(p0: AdValue?) {
                TODO("Not yet implemented")
            }
        }
        super.onBackPressed()
    }
}