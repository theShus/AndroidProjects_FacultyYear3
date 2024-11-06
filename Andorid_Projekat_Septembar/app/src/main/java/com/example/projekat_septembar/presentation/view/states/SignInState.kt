package com.example.projekat_septembar.presentation.view.states


import com.example.projekat_septembar.data.models.serverResponses.UserDetails

sealed class SignInState {
    object DataFetched: SignInState()
    data class Success(val signInResponse: UserDetails): SignInState()
    data class Existing(val message: String): SignInState()
    data class Error(val message: String): SignInState()
}