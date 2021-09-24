package com.example.worldbeer.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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

        val activity = requireActivity() as AppCompatActivity
        val toolbar: androidx.appcompat.widget.Toolbar? = activity.findViewById(R.id.toolbar)
        activity.setSupportActionBar(toolbar)
        activity.toolbar.title = beerItem?.name

        activity.supportActionBar?.show()

        initView()

        return binding.root
    }

    private fun initView() {
        binding.tvBrewersTips.text = beerItem?.brewersTips.toString()
        binding.tvFirstBrewed.text = beerItem?.firstBrewed.toString()
        binding.tvFoodPairing.text = beerItem?.foodPairing.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        internalBinding = null
    }
}
