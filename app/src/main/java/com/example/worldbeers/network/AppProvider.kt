package com.example.worldbeers.network

import com.example.worldbeers.ui.home.model.BeerDomain
import io.reactivex.rxjava3.core.Single

interface AppContract {
    fun getBeerList(): Single<List<BeerDomain>>
}

class AppProvider(private val backend: AppBackend) : AppContract {
    override fun getBeerList(): Single<List<BeerDomain>> = backend.getBeerList()
        .map { listBeer -> listBeer.map { it.toDomain() } }
}
