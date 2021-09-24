package com.example.worldbeer.ui.detail

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.worldbeer.R
import com.example.worldbeer.databinding.DetailScreenBinding
import com.example.worldbeer.base.BaseFragment
import com.example.worldbeer.ui.home.model.BeerDomain
import com.example.worldbeer.utils.KeyUtils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.main_screen.*
import org.koin.android.ext.android.inject

class DetailScreen : BaseFragment() {

    private var internalBinding: DetailScreenBinding? = null
    private var beerItem: BeerDomain? = null
    private val gson: Gson by inject()

    private val binding
        get() = internalBinding!!

    override val tAG
        get() = this.javaClass.kotlin.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        internalBinding = DetailScreenBinding.inflate(inflater, container, false)

        if (arguments != null) {
            val beerItemString = requireArguments().getString(KeyUtils.BEER_ITEM, "")
            beerItem = gson.fromJson(beerItemString, BeerDomain::class.java)
        }

        initToolbar()

        initView()

        return binding.root
    }

    private fun initToolbar() {
        val activity = requireActivity() as AppCompatActivity
        val toolbar: Toolbar? = activity.findViewById(R.id.toolbar)
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setDisplayShowHomeEnabled(true)
        activity.toolbar.title = beerItem?.name

        activity.supportActionBar?.show()
        activity.toolbar?.setNavigationOnClickListener {
            popBackStack()
        }
    }

    private fun initView() {
        binding.tvBrewersTips.text = String.format(getString(R.string.brewers_tips_s), beerItem?.brewersTips.toString())
        binding.tvFirstBrewed.text = String.format(getString(R.string.first_brewed_s), beerItem?.firstBrewed.toString())
        binding.tvFoodPairing.text = String.format(getString(R.string.food_pairing_s), beerItem?.foodPairing.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        internalBinding = null
    }
}
