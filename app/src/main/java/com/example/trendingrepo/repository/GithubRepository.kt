package com.example.trendingrepo.repository

import com.example.trendingrepo.model.TrendingResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface GithubRepository {

    fun getTrendingListAsync(
        language: String?,
        since: String?
    ): Deferred<Response<List<TrendingResponse>>>
}