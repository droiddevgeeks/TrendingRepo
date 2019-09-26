package com.example.trendingrepo.base.extensions


import com.example.trendingrepo.base.model.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


suspend inline fun <T> Deferred<Response<T>>.awaitAndGet(): Result<T?> {
    return try {
        val response = await()
        if (response.isSuccessful) Result.Success(response.body(), response.code())
        else
            Result.Failure(Throwable(response.message()))
    } catch (e: Exception) {
        when (e) {
            is UnknownHostException,
            is TimeoutException,
            is ConnectException -> {
            }
        }
        Result.Failure(e)
    }
}
