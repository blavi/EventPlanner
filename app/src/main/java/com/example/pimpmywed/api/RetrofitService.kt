package com.example.pimpmywed.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private var logging   = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    private var client : OkHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()
    private const val baseURL : String = "https://sheets.googleapis.com/v4/spreadsheets/"

    private var retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}