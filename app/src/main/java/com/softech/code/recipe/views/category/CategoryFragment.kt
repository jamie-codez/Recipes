package com.softech.code.recipe.views.category

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.softech.code.recipe.R
import com.softech.code.recipe.adapter.RecyclerViewMealByCategory
import com.softech.code.recipe.database.FavoriteRepository
import com.softech.code.recipe.databinding.FragmentCategoryBinding
import com.softech.code.recipe.model.Categories
import com.softech.code.recipe.model.Meals
import com.softech.code.recipe.utils.Utils
import com.softech.code.recipe.views.detail.DetailActivity
import com.squareup.picasso.Picasso

class CategoryFragment : Fragment(), CategoryView {
    private lateinit var binding: FragmentCategoryBinding

    private lateinit var descDialog: AlertDialog.Builder
    private lateinit var repository: FavoriteRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        binding.cardCategory.setOnClickListener { onClick() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository =
            FavoriteRepository(requireActivity().application)

        if (arguments != null) {
            binding.textCategory.text = requireArguments().getString("EXTRA_DATA_DESC")
            Picasso.get()
                .load(requireArguments().getString("EXTRA_DATA_IMAGE"))
                .into(binding.imageCategory)
            Picasso.get()
                .load(requireArguments().getString("EXTRA_DATA_IMAGE"))
                .into(binding.imageCategoryBg)
            descDialog = AlertDialog.Builder(requireActivity())
                .setTitle(requireArguments().getString("EXTRA_DATA_NAME"))
                .setMessage(requireArguments().getString("EXTRA_DATA_DESC"))
            val presenter = CategoryPresenter(this)
            presenter.getMealByCategory(requireArguments().getString("EXTRA_DATA_NAME"))
        }

        MobileAds.initialize(
            requireActivity().applicationContext,
            resources.getString(R.string.banner_ads_id)
        )

        val adRequest: AdRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun onClick() {
        descDialog.setPositiveButton("CLOSE") {
                dialog: DialogInterface, _: Int -> dialog.dismiss()
        }
        descDialog.show()
    }

    override fun showLoading() {
        binding.progressBar.visibility = VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = GONE
    }

    override fun setMeal(meals: List<Meals.Meal>) {
        val mAdapter = RecyclerViewMealByCategory(requireActivity(), meals, repository!!,null)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 2)
            clipToPadding = false
            adapter = mAdapter
        }
        mAdapter.notifyDataSetChanged()
        mAdapter.setOnItemClickListener(object:RecyclerViewMealByCategory.OnItemClick{
            override fun onClick(position: Int) {
               val intent = Intent(activity,DetailActivity::class.java)
                intent.putExtra("detail",meals[position].strMeal)
                startActivity(intent)
            }
        })
    }

    override fun setCategory(category: List<Categories.Category>) {
        TODO("Not yet implemented")
    }

    override fun setErrorLoading(message: String) {
        Utils.showAlertDialogMessage(requireActivity(), "Error ", message)
    }
}