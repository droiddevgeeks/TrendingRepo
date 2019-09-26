package com.example.trendingrepo.repository

import com.example.trendingrepo.api.GithubApi
import com.example.trendingrepo.model.TrendingResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

class GithubRepositoryImpl(private val githubApi: GithubApi) : GithubRepository {

    override fun getTrendingListAsync(
        language: String?,
        since: String?
    ): Deferred<Response<List<TrendingResponse>>> =
        githubApi.getTrendingListAsync(language, since)
}