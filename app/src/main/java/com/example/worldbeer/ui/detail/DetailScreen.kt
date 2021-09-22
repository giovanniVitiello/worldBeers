package com.example.worldbeer.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.worldbeer.R
import com.example.worldbeer.databinding.DetailScreenBinding
import com.example.worldbeer.base.BaseFragment

class DetailScreen : BaseFragment() {

    private var internalBinding: DetailScreenBinding? = null

    private val binding
        get() = internalBinding!!

    override val tAG
        get() = this.javaClass.kotlin.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        internalBinding = DetailScreenBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        binding.button.setOnClickListener {
            safeNavigate(R.id.homeScreen)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        internalBinding = null
    }
}
