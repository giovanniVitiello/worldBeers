package com.example.worldbeer.ui.home

import com.example.worldbeer.network.AppContract
import com.example.worldbeer.ui.home.model.BeerDomain
import com.example.worldbeer.utils.BaseViewModel
import com.example.worldbeer.utils.exhaustive
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable

sealed class HomeEvent {
    object LoadData : HomeEvent()
}

sealed class HomeState {
    object InProgress : HomeState()
    data class LoadedData(val data: List<BeerDomain>) : HomeState()
    data class Error(val error: Throwable) : HomeState()
}

class HomeViewModel(
    private val scheduler: Scheduler,
    private val contract: AppContract
) : BaseViewModel<HomeState, HomeEvent>() {

    private var dataSubscription = Disposable.disposed()

    override fun send(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadData -> loadDetailData()
        }.exhaustive
    }

    private fun loadDetailData() {
        if (dataSubscription.isDisposed) {
            post(HomeState.InProgress)
            dataSubscription = contract.getBeerList()
                .observeOn(scheduler)
                .subscribe(
                    { data -> post(HomeState.LoadedData(data)) },
                    { error -> post(HomeState.Error(error)) }
                )
            disposables.add(dataSubscription)
        }
    }
}
