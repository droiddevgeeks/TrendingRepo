package com.example.trendingrepo.base.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel(private val app: Application) : AndroidViewModel(app), CoroutineScope {


    private val failedJobIdList = mutableSetOf<String>()

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private fun addJobId(jobId: String) {
        failedJobIdList.add(jobId)
    }

    private fun removeJobId(jobId: String) {
        failedJobIdList.remove(jobId)
    }

    protected fun CoroutineScope.launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        doOnComplete: (throwable: Throwable?) -> Unit = {},
        id: String,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return launch(context, start, block)
            .apply {
                invokeOnCompletion {
                    if (it != null) addJobId(id)
                    else removeJobId(id)

                    doOnComplete(it)
                }
            }
    }


    override fun onCleared() {
        super.onCleared()
        failedJobIdList.clear()
        job.cancel()
    }

}