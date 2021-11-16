package com.example.worldbeers.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.worldbeers.network.AppContract
import com.example.worldbeers.ui.home.model.BeerDomain
import com.example.worldbeers.utils.BaseViewModel
import com.example.worldbeers.utils.Resource
import com.example.worldbeers.utils.exhaustive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

sealed class HomeEventCoroutines {
    object LoadData : HomeEventCoroutines()
}

class HomeViewModelCoroutines(
    private val contract: AppContract
) : BaseViewModel<HomeEventCoroutines>() {

    private val _liveData = MutableLiveData<Resource<List<BeerDomain>>>()
    val liveData : LiveData<Resource<List<BeerDomain>>>
        get() {
            return _liveData
        }

    private var currentSearchResult: Flow<PagingData<BeerDomain>>? = null

    override fun send(event: HomeEventCoroutines) {
        when (event) {
            is HomeEventCoroutines.LoadData -> loadDetailData()
        }.exhaustive
    }

    fun loadDetailData(hasNetwork: Boolean = true) {
        _liveData.postValue(Resource.loading(null))
        viewModelScope.launch {
            _liveData.postValue(contract.getBeerListCoroutines(hasNetwork))
        }
    }

    fun loadPagingData(hasNetwork: Boolean = true): Flow<PagingData<BeerDomain>> {
        val lastResult = currentSearchResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult = contract.getBeerListPagingCoroutines().cachedIn(viewModelScope)

        currentSearchResult = newResult
        return newResult
    }
}
