package com.example.projekat_septembar.presentation.view.states


sealed class SignUpState {
    object DataFetched: SignUpState()
    data class Success(val singUpResponse: String): SignUpState()
    data class Error(val message: String): SignUpState()
    data class RegisterCheck(val found: Int): SignUpState()


}