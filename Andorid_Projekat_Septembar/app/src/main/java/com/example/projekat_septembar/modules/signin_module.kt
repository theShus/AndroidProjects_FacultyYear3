package com.example.projekat_septembar.modules


import com.example.projekat_septembar.data.datasources.local.UserDataBase
import com.example.projekat_septembar.data.datasources.remote.SignDataSource
import com.example.projekat_septembar.data.repositories.SignRepository
import com.example.projekat_septembar.data.repositories.SignRepositoryImpl
import com.example.projekat_septembar.presentation.viewmodel.SignViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val signInModule = module {


    viewModel { SignViewModel(signRepository = get()) }

    single<SignRepository> { SignRepositoryImpl(localDataSource = get(),signDataSource = get() )}

    single { get<UserDataBase>().getUserDao() }

    single<SignDataSource> { create(retrofit = get()) }


}