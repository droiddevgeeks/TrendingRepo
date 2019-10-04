package com.example.trendingrepo.ui.main

import android.view.View
import androidx.lifecycle.Observer
import com.example.trendingrepo.base.common.EventObserver
import com.example.trendingrepo.base.core.BaseFragment
import com.example.trendingrepo.base.extensions.showShortToast
import com.example.trendingrepo.base.helper.ApplicationUtil
import com.example.trendingrepo.base.model.DataState
import com.example.trendingrepo.model.TrendingResponse
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class AbstractMainFragment : BaseFragment<MainViewModel>() {

    override val viewModel: MainViewModel by sharedViewModel()

    override fun viewInitialization(view: View) {
        fetchData()
        observeDataChange()
    }

    fun fetchData(language: String? = "kotlin", since: String? = "daily") {
        if (ApplicationUtil.hasNetwork(context!!))
            viewModel.getTrendingData(language, since)
        else
            onFailRetry()
    }

    private fun observeDataChange() {
        viewModel.trendingLiveData.observe(viewLifecycleOwner, EventObserver { trendingData ->
            when (trendingData) {
                is DataState.Success -> {
                    setTrendingData(trendingData.data)
                }
                is DataState.Failure -> {
                    context?.showShortToast(trendingData.errorMessage)
                    onFailRetry()
                }
            }
        })

        viewModel.loadingState.observe(viewLifecycleOwner, Observer { showLoadingState(it) })
    }

    abstract fun setTrendingData(list: List<TrendingResponse>)
    abstract fun showLoadingState(loading: Boolean)
    abstract fun onFailRetry()
}