package com.example.projekat_septembar.modules

import com.example.projekat_septembar.data.datasources.local.CarDataBase
import com.example.projekat_septembar.data.datasources.remote.CarDataSource
import com.example.projekat_septembar.data.repositories.CarRepository
import com.example.projekat_septembar.data.repositories.CarRepositoryImpl
import com.example.projekat_septembar.presentation.viewmodel.CarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val car_module = module {

    viewModel { CarViewModel (carRepository = get()) }

    single<CarRepository> { CarRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single { get<CarDataBase>().getCarDao() }

    single<CarDataSource> { create(retrofit = get()) }

}