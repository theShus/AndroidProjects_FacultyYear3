package com.example.projekat_septembar.presentation.contract

import androidx.lifecycle.LiveData
import com.example.projekat_septembar.presentation.view.states.SignInState
import com.example.projekat_septembar.presentation.view.states.SignUpState

interface SignContract {

    interface SignViewModel{
        val signInState: LiveData<SignInState>
        val signUpState: LiveData<SignUpState>

        fun signIn(username: String, password: String): Boolean
        fun signUp(name: String, lastname: String, phone: Long ,country: String): Boolean

        fun checkSignIn(username: String, password: String)
        fun registerUser(username: String,password: String, name: String, lastname: String, country: String, phone: Long)
        fun checkByCredentials(username: String, name: String, lastname: String, country: String, phone: Long)
    }
}