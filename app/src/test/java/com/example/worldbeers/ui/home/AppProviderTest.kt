package com.example.worldbeers.ui.home

import com.example.worldbeers.network.AppContract
import com.example.worldbeers.ui.home.model.BeerDomain
import com.example.worldbeers.utils.Resource
import io.reactivex.rxjava3.core.Single

class AppProviderTest : AppContract {

    private lateinit var singleListBeer : Single<List<BeerDomain>>
    private val listBeer = mutableListOf<BeerDomain>()

    override fun getBeerList(): Single<List<BeerDomain>> {
        return singleListBeer
    }

    override suspend fun getBeerListCoroutines(hasNetwork : Boolean): Resource<List<BeerDomain>> {
        return if(hasNetwork) {
            Resource.success(listBeer)
        } else {
            Resource.error("Error", null)
        }
    }
}
