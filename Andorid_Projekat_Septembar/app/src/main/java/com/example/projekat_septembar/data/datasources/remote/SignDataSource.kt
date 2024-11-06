package com.example.projekat_septembar.data.datasources.remote

import com.example.projekat_septembar.data.models.serverRequests.SignInRequest
import com.example.projekat_septembar.data.models.serverRequests.SignUpRequest
import com.example.projekat_septembar.data.models.serverResponses.SignInResponse
import com.example.projekat_septembar.data.models.serverResponses.SignUpResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface SignDataSource {

    @POST("api/login")
    fun signIn(@Body body: SignInRequest): Observable<SignInResponse>

    @POST("api/signup")
    fun signUp(@Body body: SignUpRequest): Observable<SignUpResponse>

}