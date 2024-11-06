package com.example.projekat_septembar.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekat_septembar.data.repositories.SignRepository
import com.example.projekat_septembar.presentation.contract.SignContract
import com.example.projekat_septembar.presentation.view.states.SignInState
import com.example.projekat_septembar.presentation.view.states.SignUpState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class SignViewModel(private val signRepository: SignRepository) : ViewModel(), SignContract.SignViewModel {

    private val subscriptions = CompositeDisposable()
    override val signInState: MutableLiveData<SignInState> = MutableLiveData()
    override val signUpState: MutableLiveData<SignUpState> = MutableLiveData()


    override fun signIn(username: String, password: String): Boolean {
        val subscription = signRepository
            .signIn(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    signInState.value = SignInState.Success(it)
                },
                {
                    signInState.value = SignInState.Error("You've entered wrong data")
                    Timber.e(it)

                },
                {
                    Timber.e("Completed")
                }
            )
        subscriptions.add(subscription)
        return true
    }

    override fun signUp(name: String, lastname: String, phone: Long, country: String): Boolean {
        val subscription = signRepository
            .signUp(name, lastname, phone ,country )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    signUpState.value = SignUpState.Success(it)
                },
                {
                    signUpState.value = SignUpState.Error("You've entered wrong data")
                    Timber.e(it)
                },
                {
                    Timber.e("Completed")
                }
            )
        subscriptions.add(subscription)
        return true
    }

    override fun checkSignIn(username: String, password: String) {
        val subscription = signRepository
            .checkSignIn(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                   if (it != 0) signIn(username, password)
                    else signInState.value = SignInState.Existing("You have entered wrong credentials")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun registerUser(username: String,password: String, name: String, lastname: String, country: String, phone: Long) {
        val subscription = signRepository
            .registerUser(username, password, name, lastname, country, phone)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                   Timber.e("User registered")
                    signUp(name, lastname, phone, country)
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun checkByCredentials(username: String, name: String,lastname: String,country: String,phone: Long) {
        var once = true//sluzi da zaustavi ponovno okidanje jer se izvrsava jos jednom nakon promene u bazi
        val subscription = signRepository
            .checkByCredentials(username, name, lastname, country, phone)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (once){
                        signUpState.value = SignUpState.RegisterCheck(it)
                        once = false
                    }
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

}