package com.example.trendingrepo.base.retrofit

import android.app.Application
import com.example.trendingrepo.api.Constants
import com.example.trendingrepo.base.helper.ApplicationUtil
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.lang.Exception
import java.util.concurrent.TimeUnit

object NetworkBuilder {

    const val BASE_URL = "https://github-trending-api.now.sh/"

    fun <T> create(
        application: Application,
        baseUrl: String,
        apiType: Class<T>
    ) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(gmHttpClient(application))
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(apiType)

    private fun gmHttpClient(application: Application): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor(logging)
            .addInterceptor { provideCacheInterceptor(it, application) }
            .addInterceptor { offlineCacheInterceptor(it, application) }
            .cache(provideCache(application))

        return builder.build()
    }

    private fun provideCache(application: Application): Cache? {
        var cache: Cache? = null
        try {
            val file = File(application.cacheDir, "http-cache")
            cache = Cache(file, 5 * 1024 * 1024) //5MB
        } catch (e: Exception) {
        }
        return cache
    }

    private fun provideCacheInterceptor(
        chain: Interceptor.Chain,
        application: Application
    ): Response? {

        val response: Response = chain.proceed(chain.request())
        var cacheControl: CacheControl? = null
        cacheControl = if (ApplicationUtil.hasNetwork(application)) {
            CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build()
        } else {
            CacheControl.Builder().maxStale(2, TimeUnit.HOURS).build()
        }

        return response.newBuilder()
            .removeHeader(Constants.HEADER_PRAGMA)
            .removeHeader(Constants.HEADER_CACHE_CONTROL)
            .header(Constants.HEADER_CACHE_CONTROL, cacheControl.toString())
            .build()
    }

    private fun offlineCacheInterceptor(
        chain: Interceptor.Chain,
        application: Application
    ): Response? {

        var request: Request = chain.request()
        val cacheControl: CacheControl
        if (!ApplicationUtil.hasNetwork(application)) {
            cacheControl = CacheControl.Builder().maxStale(2, TimeUnit.HOURS).build()
            request = request.newBuilder()
                .removeHeader(Constants.HEADER_PRAGMA)
                .removeHeader(Constants.HEADER_CACHE_CONTROL)
                .cacheControl(cacheControl).build()
        }
        return chain.proceed(request)
    }
}

