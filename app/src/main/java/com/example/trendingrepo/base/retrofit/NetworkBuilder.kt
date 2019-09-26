package com.example.trendingrepo.base.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkBuilder {

    const val BASE_URL = "https://github-trending-api.now.sh/"

    fun <T> create(
        baseUrl: String,
        apiType: Class<T>
    ) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(gmHttpClient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(apiType)

    private fun gmHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor(logging)

        return builder.build()
    }
}

