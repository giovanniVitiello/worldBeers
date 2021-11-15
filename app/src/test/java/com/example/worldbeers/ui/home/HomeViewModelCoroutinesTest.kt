package com.example.worldbeers.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.worldbeers.MainCoroutineRule
import com.example.worldbeers.getOrAwaitValue
import com.example.worldbeers.observeForTesting
import com.example.worldbeers.ui.home.model.BeerDomain
import com.example.worldbeers.utils.BaseViewModel
import com.example.worldbeers.utils.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelCoroutinesTest : BaseViewModel<HomeEventCoroutines>() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val listFlange = mutableListOf<BeerDomain>()
    private lateinit var homeViewModelCoroutines: HomeViewModelCoroutines

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        homeViewModelCoroutines = HomeViewModelCoroutines(AppProviderTest())

    }

    @ExperimentalCoroutinesApi
    @Test
    fun`test State Success after mock api call`(){
        homeViewModelCoroutines.loadDetailData()
        homeViewModelCoroutines.liveData.observeForTesting {
            val value = homeViewModelCoroutines.liveData.getOrAwaitValue()
            assertThat(value).isEqualTo(Resource.success(listFlange))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun`test State is different response after mock api call`(){
        val newListBeer = mutableListOf(BeerDomain("1","a","e","1","a","e", listOf("1"),"a"))
        homeViewModelCoroutines.loadDetailData()
        homeViewModelCoroutines.liveData.observeForTesting {
            val value = homeViewModelCoroutines.liveData.getOrAwaitValue()
            assertThat(value).isNotEqualTo(Resource.success(newListBeer))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun`api call return error`(){
        homeViewModelCoroutines.loadDetailData(false)
        homeViewModelCoroutines.liveData.observeForTesting {
            val value = homeViewModelCoroutines.liveData.getOrAwaitValue()
            assertThat(value).isEqualTo(Resource.error("Error", null))
        }
    }

    override fun send(event: HomeEventCoroutines) {

    }

}
