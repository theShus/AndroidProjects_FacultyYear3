package com.example.projekat_septembar.data.repositories

import com.example.projekat_septembar.data.datasources.local.CarDao
import com.example.projekat_septembar.data.datasources.remote.CarDataSource
import com.example.projekat_septembar.data.models.Car
import com.example.projekat_septembar.data.models.CarEntity
import com.example.projekat_septembar.data.models.Resource
import com.example.projekat_septembar.data.models.SellerDetails
import com.example.projekat_septembar.data.models.serverRequests.ContactSellerRequest
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber

class CarRepositoryImpl (private val localDataSource: CarDao, private val remoteDataSource: CarDataSource) : CarRepository {

    override fun fetchAllFromServer(): Observable<List<Car>> {
        return remoteDataSource
            .fetchAll()
            .map {
                it.cars.map { carResponse ->
                    Car(
                        id = carResponse.id,
                        car = carResponse.car,
                        car_model = carResponse.car_model,
                        car_color = carResponse.car_color,
                        car_model_year = carResponse.car_model_year,
                        car_vin = carResponse.car_vin,
                        price = carResponse.price,
                        availability = carResponse.availability,
                    )
                }
            }
    }

    override fun getSeller(id: Long): Observable<SellerDetails> {
        return remoteDataSource
            .getSeller("api/users/$id")
            .map {
                SellerDetails(
                    first_name = it.User.first_name,
                    last_name = it.User.last_name,
                    phone = it.User.phone,
                    avatar = it.User.avatar,
                    email = it.User.email,
                )
            }
    }

    override fun contactSeller(firstname: String, lastname: String, message: String, contact: Int): Observable<Resource<Unit>> {
        return remoteDataSource
            .contactSeller(ContactSellerRequest(firstname, lastname, message, contact))
            .map {
                Resource.Success(Unit)
            }
    }

    override fun search(type: String, key: String): Observable<List<Car>> {

        var searchUrl = ""

        when(type){
            "name" -> searchUrl = "api/cars/name/$key"
            "model" -> searchUrl = "api/cars/model/$key"
            "color" -> searchUrl = "api/cars/color/$key"
            else -> Timber.e("Error while searching")
        }

        return remoteDataSource
            .searchCars(searchUrl)
            .map {
                it.Cars.map { carResponse ->
                    Car(
                        id = carResponse.id,
                        car = carResponse.car,
                        car_model = carResponse.car_model,
                        car_color = carResponse.car_color,
                        car_model_year = carResponse.car_model_year,
                        car_vin = carResponse.car_vin,
                        price = carResponse.price,
                        availability = carResponse.availability,
                    )
                }
            }
    }


    override fun saveCarToDb(car: Car): Completable {
        return localDataSource.insert(CarEntity(
            0,
            car.car,
            car.car_model,
            car.car_color,
            car.car_model_year,
            car.car_vin,
            car.price,
            car.availability,
            ))
    }

    override fun getAllCars(): Observable<List<Car>> {
        return localDataSource
            .getAll()
            .map { it ->
                it.map {
                    Car(
                        it.id,
                        it.car,
                        it.car_model,
                        it.car_color,
                        it.car_model_year,
                        it.car_vin,
                        it.price,
                        it.availability,
                    )
                }
            }
    }

    override fun deleteById(id: Long): Completable {
        return localDataSource.deleteById(id)
    }

}