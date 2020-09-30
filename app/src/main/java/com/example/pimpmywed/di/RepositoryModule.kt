package com.example.pimpmywed.di

import com.example.pimpmywed.api.ApiProvider
import com.example.pimpmywed.database.GuestsDAO
import com.example.pimpmywed.repository.PersonsRepository
import com.example.pimpmywed.repository.PersonsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun provideCountryRepository(api: ApiProvider, dao : GuestsDAO): PersonsRepository {
        return PersonsRepositoryImpl(api, dao)
    }
    single { provideCountryRepository(get(), get()) }

}