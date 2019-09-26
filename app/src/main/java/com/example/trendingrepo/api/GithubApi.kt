package com.example.trendingrepo.api

import com.example.trendingrepo.base.model.ServerResponse
import com.example.trendingrepo.model.TrendingResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("repositories")
    fun getTrendingListAsync(
        @Query("language") language: String?=null,
        @Query("since") since: String?=null
    ): Deferred<Response<ServerResponse<List<TrendingResponse>>>>

}