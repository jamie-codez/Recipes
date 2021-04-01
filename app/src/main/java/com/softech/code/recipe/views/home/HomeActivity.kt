package com.softech.code.recipe.views.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.softech.code.recipe.R
import com.softech.code.recipe.adapter.RecyclerViewHomeAdapter
import com.softech.code.recipe.adapter.ViewPagerHeaderAdapter
import com.softech.code.recipe.databinding.ActivityHomeBinding
import com.softech.code.recipe.model.Categories
import com.softech.code.recipe.model.Meals
import com.softech.code.recipe.utils.Utils

class HomeActivity : AppCompatActivity(), HomeView {

    val EXTRA_CATEGORY = "category"
    val EXTRA_POSITION = "position"
    val EXTRA_DETAIL = "detail"
    lateinit var binding: ActivityHomeBinding
    lateinit var presenter: HomePresenter
    private var mInterstitialAd: InterstitialAd?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = HomePresenter(this);
        presenter.getMeals()
        presenter.getCategories()
        MobileAds.initialize(this.applicationContext, resources.getString(R.string.banner_ads_id))


        val adRequest: AdRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("TAG", adError.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("TAG", "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
    }


    override fun showLoading() {
//        binding.shimmerMeal.shimmerViewContainer2.visibility = VISIBLE
//        binding.shimmerCategory.shimmerCategoryContainer.visibility = VISIBLE
        findViewById<View>(R.id.shimmerMeal).visibility = VISIBLE
        findViewById<View>(R.id.shimmerCategory).visibility = VISIBLE
    }

    override fun hideLoading() {
        findViewById<View>(R.id.shimmerMeal).visibility = View.GONE
        findViewById<View>(R.id.shimmerCategory).visibility = View.GONE
    }

    override fun setMeal(meal: List<Meals.Meal>) {
        val headerAdapter = ViewPagerHeaderAdapter(meal, this)
        binding.viewPagerHeader.adapter = headerAdapter
        binding.viewPagerHeader.setPadding(20, 0, 20, 0)
        headerAdapter.notifyDataSetChanged()

//        headerAdapter.setOnItemClickListener { view, position ->
//            val mealName: TextView = view.findViewById(R.id.mealName)
//            val intent = Intent(applicationContext, DetailActivity::class.java)
//            intent.putExtra("detail", mealName.text.toString())
//            startActivity(intent)
//        }
    }

    override fun setCategory(category: List<Categories.Category>) {
        val homeAdapter = RecyclerViewHomeAdapter(category, this)
        binding.recyclerCategory.adapter = homeAdapter
        val layoutManager = GridLayoutManager(
            this, 3,
            GridLayoutManager.VERTICAL, false
        )
        binding.recyclerCategory.layoutManager = layoutManager
        binding.recyclerCategory.isNestedScrollingEnabled = true
        homeAdapter.notifyDataSetChanged()

//        homeAdapter.setOnItemClickListener { view, position ->
//            val intent = Intent(this, CategoryActivity::class.java)
//            intent.putExtra(HomeActivity.EXTRA_CATEGORY, category as Serializable)
//            intent.putExtra(HomeActivity.EXTRA_POSITION, position)
//            startActivity(intent)
//        }
    }

    override fun setErrorLoading(message: String) {
        Utils.showAlertDialogMessage(this, "Title", message)
    }

    override fun onBackPressed() {
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show()
//        } else {
//            finish()
//            Log.d("TAG", "The interstitial wasn't loaded yet.")
//        }
//        mInterstitialAd.setAdListener(object : AdListener() {
//            fun onAdClosed() {
//                val handler = Handler()
//                handler.postDelayed({ supportFinishAfterTransition() }, 200)
//            }
//        })
        super.onBackPressed()
    }
}