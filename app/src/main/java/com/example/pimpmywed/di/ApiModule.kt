package com.example.pimpmywed.di

import com.example.pimpmywed.api.ApiEndpoints
import com.example.pimpmywed.api.ApiProvider
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideApi(retrofit: Retrofit): ApiEndpoints {
        return retrofit.create(ApiEndpoints::class.java)
    }
    single { provideApi(get()) }

    single<ApiProvider> { ApiProvider(get()) }
}