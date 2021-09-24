package com.example.worldbeer.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldbeer.R
import com.example.worldbeer.databinding.HomeScreenBinding
import com.example.worldbeer.base.BaseFragment
import com.example.worldbeer.ui.home.model.BeerDomain
import com.example.worldbeer.utils.KeyUtils.Companion.BEER_ITEM
import com.example.worldbeer.utils.exhaustive
import com.example.worldbeer.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.main_screen.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class HomeScreen : BaseFragment() {

    private var internalBinding: HomeScreenBinding? = null
    private val homeViewModel: HomeViewModel by inject()
    private val gson: Gson by inject()
    private var beerList = mutableListOf<BeerDomain>()
    private lateinit var searchItemAdapter: SearchItemAdapter
    private lateinit var searchListenerInterface: ListenerSearchInterface

    private val newsItemListener = HomeAdapter.OnClickListener { content ->
        val bundle = Bundle()
        bundle.putString(BEER_ITEM, gson.toJson(content))
        findNavController().navigate(R.id.detailScreen, bundle)
    }

    private val binding
        get() = internalBinding!!

    override val tAG
        get() = this.javaClass.kotlin.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        internalBinding = HomeScreenBinding.inflate(inflater, container, false)

//        requireActivity().toolbar.visibility = View.GONE

        homeViewModel.send(HomeEvent.LoadData)
        initView()
        observeViewModel()


        return binding.root
    }

    private fun initView() {
        binding.pullToRefresh.setOnRefreshListener {
            beerList.clear()
            homeViewModel.send(HomeEvent.LoadData)
            binding.pullToRefresh.isRefreshing = false
        }

        searchListenerInterface = object : ListenerSearchInterface{
            override fun onClickSearch(itemElement: BeerDomain) {
                binding.searchProductResults.visibility = View.GONE
                binding.searchAsset.text?.clear()
                requireContext().hideKeyboard()
                val bundle = Bundle()
                bundle.putString(BEER_ITEM, gson.toJson(itemElement))
                findNavController().navigate(R.id.detailScreen, bundle)
            }
        }

        binding.searchAsset.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(editable: Editable) {
                if (editable.toString().isNotEmpty()) {
                    val tmpList = mutableListOf<BeerDomain>()
                    beerList.forEach {
                        if (it.name.startsWith(editable.toString(), true)) {
                            tmpList.add(it)
                        }
                    }
                    binding.searchProductResults.apply {
                        searchItemAdapter = SearchItemAdapter(searchListenerInterface)
                        adapter = searchItemAdapter
                        itemAnimator = DefaultItemAnimator()
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        binding.searchProductResults.visibility = View.VISIBLE
                        searchItemAdapter.submitList(tmpList)
                    }
                } else {
                    binding.searchProductResults.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun observeViewModel() {
        homeViewModel.observe(lifecycleScope) {
            when (it) {
                is HomeState.InProgress -> showProgressBar()
                is HomeState.LoadedData -> showList(it.data)
                is HomeState.Error -> showError(it.error.message.toString())
            }.exhaustive
        }
    }

    private fun showList(data: List<BeerDomain>) {
        binding.progressBarMain.visibility = View.GONE
        beerList = data.toMutableList()
        binding.rvProductResults.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeScreen.requireContext())
            adapter = HomeAdapter(beerList, newsItemListener)
        }
    }

    private fun showProgressBar() {
        binding.progressBarMain.visibility = View.VISIBLE
    }

    private fun showError(error: String) {
        binding.progressBarMain.visibility = View.GONE
        Timber.w("error during house creation: $error")
        showSnackBarRetry()
    }

    private fun showSnackBarRetry() {
        Snackbar.make(binding.clHomeScreen, resources.getString(R.string.network_error), Snackbar.LENGTH_INDEFINITE)
            .setAction(resources.getString(R.string.retry)) {
                homeViewModel.send(HomeEvent.LoadData)
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        internalBinding = null
    }
}
