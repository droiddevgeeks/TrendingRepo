package com.example.trendingrepo.ui.main

import org.junit.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer
import com.example.trendingrepo.TrendingApp
import com.example.trendingrepo.api.GithubApi
import com.example.trendingrepo.base.common.Event
import com.example.trendingrepo.base.model.DataState
import com.example.trendingrepo.extensions.toDeferred
import com.example.trendingrepo.model.TrendingResponse
import com.example.trendingrepo.repository.GithubRepository
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.*
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.*
import org.mockito.Mock

@RunWith(JUnit4::class)
class MainViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repo: GithubRepository

    @Mock
    lateinit var githubApi: GithubApi

    @Mock
    lateinit var application: TrendingApp
    lateinit var viewModel: MainViewModel

    @Mock
    lateinit var dataObserver: Observer<Event<DataState<List<TrendingResponse>>>>

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    private val threadContext = newSingleThreadContext("UI thread")
    private val trendingList : List<TrendingResponse> = listOf()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(threadContext)
        viewModel = MainViewModel(application, repo)
    }

    @Test
    @Suppress("DeferredResultUnused")
    fun callTrendingRepoApi(){
        runBlocking {
            githubApi.getTrendingListAsync("java", "daily")
        }
    }


    @Test
    fun test_TrendingRepo_whenSuccess() {

        //Assemble
        Mockito.`when`(githubApi.getTrendingListAsync("java", "daily"))
            .thenAnswer{ return@thenAnswer trendingList.toDeferred() }

        //Act
        viewModel.trendingLiveData.observeForever(dataObserver)
        viewModel.loadingState.observeForever(loadingObserver)
        viewModel.getTrendingData("java", "daily")

        Thread.sleep(1000)

        //Verify
        verify(loadingObserver).onChanged(true)
        //verify(dataObserver).onChanged(trendingList)
        verify(loadingObserver).onChanged(false)


    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        threadContext.close()
    }
}