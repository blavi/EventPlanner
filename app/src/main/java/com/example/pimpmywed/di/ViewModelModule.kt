package com.example.pimpmywed.di

import com.example.pimpmywed.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AllTablesViewModel(get()) }

    viewModel { GuestDetailsViewModel(get()) }

    viewModel { ViewByTableViewModel(get()) }

    viewModel { SearchGuestsViewModel(get()) }

    viewModel { HomeViewModel(get()) }

    viewModel { DashboardViewModel() }
}