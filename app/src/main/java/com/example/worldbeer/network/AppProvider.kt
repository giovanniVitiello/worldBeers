package com.example.worldbeer.network

import com.example.worldbeer.ui.home.model.BeerDomain
import io.reactivex.rxjava3.core.Single

interface AppContract {
    fun getBeerList(): Single<BeerDomain>
}

class AppProvider(private val backend: AppBackend) : AppContract {
    override fun getBeerList(): Single<BeerDomain> = backend.getBeerList().map { it.toDomain() }
}
