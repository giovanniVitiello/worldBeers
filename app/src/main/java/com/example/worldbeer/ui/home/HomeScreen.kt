package com.example.regolo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.regolo.databinding.HomeScreenBinding
import com.example.regolo.base.BaseFragment

class HomeScreen : BaseFragment() {

    private var internalBinding: HomeScreenBinding? = null

    private val binding
        get() = internalBinding!!

    override val tAG
        get() = this.javaClass.kotlin.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        internalBinding = HomeScreenBinding.inflate(inflater, container, false)
        internalBinding = HomeScreenBinding.inflate(layoutInflater)

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
