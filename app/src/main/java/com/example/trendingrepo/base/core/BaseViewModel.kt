package com.example.trendingrepo.base.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(private val app: Application) : AndroidViewModel(app), CoroutineScope {


    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    open fun doOnViewAttached() {}

}