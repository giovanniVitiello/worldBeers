package com.example.worldbeer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.worldbeer.databinding.HomeScreenBinding
import com.example.worldbeer.base.BaseFragment

class HomeScreen : BaseFragment() {

    private var internalBinding: HomeScreenBinding? = null

    private val binding
        get() = internalBinding!!

    override val tAG
        get() = this.javaClass.kotlin.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        internalBinding = HomeScreenBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {

    }

    override fun onDestroy() {
        super.onDestroy()
        internalBinding = null
    }
}
