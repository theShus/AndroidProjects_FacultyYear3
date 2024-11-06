package com.example.projekat_septembar.data.repositories

import android.annotation.SuppressLint
import com.example.projekat_septembar.data.datasources.local.UserDao
import com.example.projekat_septembar.data.datasources.remote.SignDataSource
import com.example.projekat_septembar.data.models.UserEntity
import com.example.projekat_septembar.data.models.serverRequests.SignInRequest
import com.example.projekat_septembar.data.models.serverRequests.SignUpRequest
import com.example.projekat_septembar.data.models.serverResponses.UserDetails
import io.reactivex.Completable
import io.reactivex.Observable

class SignRepositoryImpl (private val localDataSource: UserDao,  private val signDataSource: SignDataSource): SignRepository {

    override fun signIn(username: String, password: String): Observable<UserDetails> {
        return signDataSource
            .signIn(
                SignInRequest(username, password, true)
            )
            .map {
                UserDetails(
                    userName = it.userDetails.userName,
                    password = it.userDetails.password,
                    verified = true
                )
            }
    }

    override fun signUp(firstname: String, lastname: String, mobile: Long, country: String): Observable<String> {
        return signDataSource
            .signUp(
                SignUpRequest(firstname, lastname, mobile, country)
            )
            .map {
                it.Message
            }
    }

    override fun registerUser(username: String,password: String, name: String, lastname: String, country: String, phone: Long): Completable {
        return localDataSource.insert(UserEntity(username,password,name,lastname, phone, country))
    }

    @SuppressLint("CheckResult")
    override fun checkByCredentials(username: String, name: String, lastname: String, country: String, phone: Long): Observable<Int> {
      return localDataSource.getByCredentials(username, name, lastname, country, phone)
    }

    override fun checkSignIn(username: String, password: String): Observable<Int> {
        return localDataSource.checkSignIn(username, password)
    }

}