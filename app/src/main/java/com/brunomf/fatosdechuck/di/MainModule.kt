package com.brunomf.fatosdechuck.di

import com.brunomf.fatosdechuck.data.api.ChuckNorrisApi
import com.brunomf.fatosdechuck.service.repository.ChuckNorrisRepository
import com.brunomf.fatosdechuck.service.repository.ChuckNorrisRepositoryImpl
import com.brunomf.fatosdechuck.service.retrofit.createApi
import com.brunomf.fatosdechuck.service.retrofit.provideOkHttpClient
import com.brunomf.fatosdechuck.service.retrofit.provideRetrofit
import com.brunomf.fatosdechuck.viewmodel.MainViewModel
import com.brunomf.fatosdechuck.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val remoteModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { createApi<ChuckNorrisApi>(get()) }
}

val repositoryModule = module {
    single<ChuckNorrisRepository> { ChuckNorrisRepositoryImpl(get()) }
}

val uiModule = module {
    viewModel { MainViewModel(repository = get()) }
    viewModel { SearchViewModel(repository = get()) }
}

