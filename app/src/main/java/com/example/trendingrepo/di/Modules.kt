package com.example.trendingrepo.di

import com.example.trendingrepo.api.GithubApi
import com.example.trendingrepo.base.retrofit.NetworkBuilder
import com.example.trendingrepo.base.retrofit.NetworkBuilder.BASE_URL
import com.example.trendingrepo.repository.GithubRepository
import com.example.trendingrepo.repository.GithubRepositoryImpl
import com.example.trendingrepo.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Modules {

    private val viewModelModules = module {
        viewModel { MainViewModel(get(), get()) }
    }

    private val networkModules = module {
        single<GithubApi> {
            NetworkBuilder.create(BASE_URL, GithubApi::class.java)
        }
    }

    private val repoModules = module {
        single<GithubRepository> { GithubRepositoryImpl(get()) }
    }

    fun getAll() = listOf(
        viewModelModules,
        networkModules,
        repoModules
    )
}