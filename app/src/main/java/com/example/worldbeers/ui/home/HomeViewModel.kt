package com.example.worldbeers.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.worldbeers.network.AppContract
import com.example.worldbeers.ui.home.model.BeerDomain
import com.example.worldbeers.utils.BaseViewModel
import com.example.worldbeers.utils.Resource
import com.example.worldbeers.utils.exhaustive
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable

sealed class HomeEvent {
    object LoadData : HomeEvent()
}

class HomeViewModel(
    private val scheduler: Scheduler,
    private val contract: AppContract
) : BaseViewModel<HomeEvent>() {

    private var dataSubscription = Disposable.disposed()
    private val _liveData = MutableLiveData<Resource<List<BeerDomain>>>()
    val liveData: LiveData<Resource<List<BeerDomain>>>
        get() {
            return _liveData
        }

    override fun send(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadData -> loadDetailData()
        }.exhaustive
    }

    private fun loadDetailData() {
        if (dataSubscription.isDisposed) {
            _liveData.postValue(Resource.loading(null))
            dataSubscription = contract.getBeerList()
                .observeOn(scheduler)
                .subscribe(
                    { data -> _liveData.postValue(data) },
                    { error -> _liveData.postValue(Resource.error(error.toString(), null)) }
                )
        }
        disposables.add(dataSubscription)
    }
}

