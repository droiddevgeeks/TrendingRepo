package com.example.trendingrepo.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trendingrepo.base.common.Event
import com.example.trendingrepo.base.core.BaseViewModel
import com.example.trendingrepo.base.extensions.awaitAndGet
import com.example.trendingrepo.base.model.DataState
import com.example.trendingrepo.model.TrendingResponse
import com.example.trendingrepo.base.model.Result
import com.example.trendingrepo.repository.GithubRepository
import kotlinx.coroutines.launch

class MainViewModel(app: Application, private val githubRepo: GithubRepository) :
    BaseViewModel(app) {


    private val _trendingLiveData by lazy { MutableLiveData<Event<DataState<List<TrendingResponse>>>>() }
    val trendingLiveData: LiveData<Event<DataState<List<TrendingResponse>>>> by lazy { _trendingLiveData }

    var loadingState = MutableLiveData<Event<Boolean>>()


    fun getTrendingData(language: String?, since: String?) {
        launch {
            loadingState.postValue(Event(true))
            when (val result = githubRepo.getTrendingListAsync(language, since).awaitAndGet()) {
                is Result.Success -> {
                    loadingState.postValue(Event(false))
                    result.body?.let {
                        Event(DataState.Success(it))
                    }.run(_trendingLiveData::postValue)
                }

                is Result.Failure -> {
                    loadingState.postValue(Event(false))
                }
            }
        }
    }

}
