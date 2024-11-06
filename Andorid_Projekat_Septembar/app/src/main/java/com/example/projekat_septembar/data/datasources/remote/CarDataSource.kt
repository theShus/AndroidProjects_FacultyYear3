package com.example.projekat_septembar.data.datasources.remote

import com.example.projekat_septembar.data.models.serverRequests.ContactSellerRequest
import com.example.projekat_septembar.data.models.serverResponses.GetCarsResponse
import com.example.projekat_septembar.data.models.serverResponses.GetSearchCarsResponse
import com.example.projekat_septembar.data.models.serverResponses.GetSellerResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface CarDataSource {

    @GET("api/cars")
    fun fetchAll(): Observable<GetCarsResponse>

    @GET
    fun getSeller(@Url url: String?): Observable<GetSellerResponse>

    @POST("api/contactus")
    fun contactSeller(@Body contactSellerRequest: ContactSellerRequest): Observable<Any>

    @GET()
    fun searchCars(@Url url: String?): Observable<GetSearchCarsResponse>


}