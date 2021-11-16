package com.example.worldbeers.network

import androidx.paging.*
import com.example.worldbeers.ui.home.model.BeerDomain
import com.example.worldbeers.utils.Resource
import com.example.worldbeers.utils.Status
import com.example.worldbeers.utils.safeApiCall
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface AppContract {
    fun getBeerList(): Single<Resource<List<BeerDomain>>>
    suspend fun getBeerListCoroutines(hasNetwork: Boolean = true): Resource<List<BeerDomain>>
    fun getBeerListPagingCoroutines(): Flow<PagingData<BeerDomain>>
}

class AppProvider(private val backend: AppBackend) : AppContract {

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
        return safeApiCall { getListBeerCoroutines() }
    }

    private suspend fun getListBeerCoroutines(): Resource<List<BeerDomain>> {
        val response = backend.getBeerListCoroutines()
        if (response.isSuccessful) {
            return response.body()?.let {
                return@let Resource.success(response.body()?.map { it.toDomain() })
            } ?: Resource.error("An unknown error occured", null)
        } else {
            return Resource.error("An unknown error occured", null)
        }
    }

    override fun getBeerListPagingCoroutines(): Flow<PagingData<BeerDomain>> =
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { GithubRepoPagingSource(backend) }
        ).flow
}

private const val INITIAL_PAGE = 1

class GithubRepoPagingSource(private val backend: AppBackend) : PagingSource<Int, BeerDomain>() {
    override fun getRefreshKey(state: PagingState<Int, BeerDomain>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BeerDomain> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val response = backend.getBeerListPagingCoroutines(page, params.loadSize / 3)
            if (response.isSuccessful) {
                LoadResult.Page(
                    data = response.body()!!.map { it.toDomain() },
                    prevKey = if (page == INITIAL_PAGE) null else page - 1,
                    nextKey = if (response.body()!!.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Throwable("An unknown error occured"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
