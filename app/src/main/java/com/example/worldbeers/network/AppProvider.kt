package com.example.worldbeers.network

import com.example.worldbeers.ui.home.model.BeerDomain
import com.example.worldbeers.utils.Resource
import io.reactivex.rxjava3.core.Single

interface AppContract {
    fun getBeerList(): Single<Resource<List<BeerDomain>>>
    suspend fun getBeerListCoroutines(hasNetwork: Boolean = true): Resource<List<BeerDomain>>
}

class AppProvider(private val backend: AppBackend): AppContract {

    override fun getBeerList(): Single<Resource<List<BeerDomain>>> {
        return try {
            backend.getBeerList().map { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        return@let Resource.success(response.body()?.map { it.toDomain() })
                    } ?: Resource.error("An unknown error occured", null)
                } else {
                    Resource.error("An unknown error occured", null)
                }
            }
        } catch (e: Throwable) {
            return backend.getBeerList().map {
                Resource.error(e.message.toString(), null)
            }
        }
    }

    override suspend fun getBeerListCoroutines(hasNetwork: Boolean): Resource<List<BeerDomain>> {
        return try {
            val response = backend.getBeerListCoroutines()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(response.body()?.map { it.toDomain() })
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch (e: Throwable) {
            return Resource.error(e.message.toString(), null)
        }

    }
}
