package com.example.trendingrepo.ui.main

import android.view.View
import com.example.trendingrepo.base.common.EventObserver
import com.example.trendingrepo.base.core.BaseFragment
import com.example.trendingrepo.base.extensions.showShortToast
import com.example.trendingrepo.base.model.DataState
import com.example.trendingrepo.model.TrendingResponse
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class AbstractMainFragment : BaseFragment<MainViewModel>() {

    override val viewModel: MainViewModel by sharedViewModel()

    override fun viewInitialization(view: View) {
        fetchData()
        observeDataChange()
    }

    fun fetchData(language: String?="java", since: String?="daily") {
        viewModel.getTrendingData(language, since)
    }

    private fun observeDataChange() {
        viewModel.trendingLiveData.observe(viewLifecycleOwner, EventObserver { trendingData ->
            when (trendingData) {
                is DataState.Success -> {
                    setTrendingData(trendingData.data)
                }
                is DataState.Failure -> {
                    context?.showShortToast(trendingData.errorMessage)
                }
            }
        })

        viewModel.loadingState.observe(viewLifecycleOwner, EventObserver{ showLoadingState(it) })
    }

    abstract fun setTrendingData(list:List<TrendingResponse>)
    abstract fun showLoadingState(loading:Boolean)
}