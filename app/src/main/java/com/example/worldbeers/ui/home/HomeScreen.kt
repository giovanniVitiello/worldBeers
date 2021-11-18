package com.example.worldbeers.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldbeers.R
import com.example.worldbeers.base.BaseFragment
import com.example.worldbeers.databinding.HomeScreenBinding
import com.example.worldbeers.ui.home.model.BeerDomain
import com.example.worldbeers.utils.KeyUtils.Companion.BEER_ITEM
import com.example.worldbeers.utils.Status
import com.example.worldbeers.utils.exhaustive
import com.example.worldbeers.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.main_screen.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class HomeScreen : BaseFragment() {

    private var internalBinding: HomeScreenBinding? = null
    private val homeViewModel: HomeViewModel by inject()
    private val homeViewModelCoroutines: HomeViewModelCoroutines by inject()
    private val gson: Gson by inject()
    private var beerList = mutableListOf<BeerDomain>()
    private lateinit var searchItemAdapter: SearchItemAdapter
    private lateinit var pagingBeersAdapter: PagingBeersAdapter
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var searchListenerInterface: ListenerSearchInterface
    private var searchJob: Job? = null

    private val newsItemListener = PagingBeersAdapter.OnClickListener { content ->
        val bundle = Bundle()
        bundle.putString(BEER_ITEM, gson.toJson(content))
        findNavController().navigate(R.id.detailScreen, bundle)
    }

    private val homeBeerItemListener = HomeAdapter.HomeOnClickListener { content ->
        val bundle = Bundle()
        bundle.putString(BEER_ITEM, gson.toJson(content))
        findNavController().navigate(R.id.detailScreen, bundle)
    }

    private val binding
        get() = internalBinding!!

    override val tAG
        get() = this.javaClass.kotlin.simpleName

    @InternalCoroutinesApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        internalBinding = HomeScreenBinding.inflate(inflater, container, false)


        // todo call different events for rxJava and Coroutines. Now i work with livedata and pagination with different adapter
//        homeViewModel.send(HomeEvent.LoadData)
//        homeViewModelCoroutines.send(HomeEventCoroutines.LoadData)

        initView()
        initToolbar()
        observeViewModel()
        loadDataPaging()
        return binding.root
    }

    @InternalCoroutinesApi
    private fun loadDataPaging() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModelCoroutines.loadPagingData().observe(viewLifecycleOwner, {
                pagingBeersAdapter.submitData(lifecycle, it)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        binding.searchAsset.text?.clear()
    }

    private fun initToolbar() {
        val activity = requireActivity() as AppCompatActivity
        val toolbar: androidx.appcompat.widget.Toolbar? = activity.findViewById(R.id.toolbar)
        activity.setSupportActionBar(toolbar)

        activity.supportActionBar?.hide()
    }

    private fun initView() {
        binding.pullToRefresh.setOnRefreshListener {
            beerList.clear()
            homeViewModel.send(HomeEvent.LoadData)
            binding.pullToRefresh.isRefreshing = false
        }

        searchListenerInterface = object : ListenerSearchInterface {
            override fun onClickSearch(itemElement: BeerDomain) {
                binding.searchProductResults.visibility = View.GONE
                binding.searchAsset.text?.clear()
                requireContext().hideKeyboard()
                updateList(mutableListOf(itemElement))
            }
        }

        pagingBeersAdapter = PagingBeersAdapter(newsItemListener, resources)

        binding.rvProductResults.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeScreen.requireContext())
            adapter = pagingBeersAdapter
        }

        binding.searchAsset.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(editable: Editable) {
                if (editable.toString().isNotEmpty()) {
                    val tmpList = mutableListOf<BeerDomain>()
                    var alphabeticalList = mutableListOf<BeerDomain>()
                    beerList = pagingBeersAdapter.snapshot().items.toMutableList()
                    beerList.forEach { beerDomain ->
                        if (beerDomain.name != null && beerDomain.description != null) {
                            if (beerDomain.name.contains(editable.toString(), true)) {
                                tmpList.add(beerDomain)
                                alphabeticalList = tmpList.sortedBy { it.name }.toMutableList()
                            } else if (tmpList.isEmpty() && beerDomain.description.contains(editable.toString(), true)) {
                                tmpList.add(beerDomain)
                                alphabeticalList = tmpList.sortedBy { it.name }.toMutableList()
                            }
                            updateList(alphabeticalList)
                        }
                    }
                    binding.searchProductResults.apply {
                        searchItemAdapter = SearchItemAdapter(searchListenerInterface)
                        adapter = searchItemAdapter
                        itemAnimator = DefaultItemAnimator()
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        binding.searchProductResults.visibility = View.VISIBLE
                        searchItemAdapter.submitList(alphabeticalList)
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
        homeViewModel.liveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> showProgressBar()
                Status.SUCCESS -> {
                    if (it.data != null) {
                        beerList = it.data.toMutableList()
                        updateList(beerList)
                    } else {
                        updateList(mutableListOf())
                    }
                }
                Status.ERROR -> showError(it.message.toString())
            }.exhaustive
        }

        homeViewModelCoroutines.liveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> showProgressBar()
                Status.SUCCESS -> {
                    if (it.data != null) {
                        beerList = it.data.toMutableList()
                        updateList(beerList)
                    } else {
                        updateList(mutableListOf())
                    }
                }
                Status.ERROR -> showError(it.message.toString())
            }.exhaustive
        }
    }

    private fun updateList(data: MutableList<BeerDomain>) {
        binding.progressBarMain.visibility = View.GONE
        binding.rvProductResults.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeScreen.requireContext())
            adapter = HomeAdapter(data, homeBeerItemListener, resources)
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
