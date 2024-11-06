package com.example.projekat_septembar.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projekat_septembar.databinding.ActivitySighUpBinding
import com.example.projekat_septembar.presentation.contract.SignContract
import com.example.projekat_septembar.presentation.view.states.SignUpState
import com.example.projekat_septembar.presentation.viewmodel.SignViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class SignUpActivity : AppCompatActivity() {

    private val signViewModel: SignContract.SignViewModel by viewModel<SignViewModel>()
    private lateinit var binding: ActivitySighUpBinding

    private var username: String = ""
    private var password: String = ""
    private var firstname: String = ""
    private var lastname: String = ""
    private var phone: Long = 0
    private var country: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySighUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        initView()
        initObservers()
    }



    private fun initView() {

        binding.btnSignUpServer.setOnClickListener{
            username = binding.inputUsernameReg.text.toString()
            password = binding.inputPasswordReg.text.toString()
            firstname = binding.firstNameEt.text.toString()
            lastname = binding.lastNameEt.text.toString()
            phone = binding.phoneEt.text.toString().toLong()
            country = binding.countryEt.text.toString()



            if(firstname.isBlank() || lastname.isBlank() || country.isBlank() || username.isBlank() || password.isBlank()){
                Toast.makeText(applicationContext,"Fields can not be empty", Toast.LENGTH_SHORT).show()
            }else {
                signViewModel.checkByCredentials(username, firstname, lastname, country, phone)
            }
        }
    }

    private fun initObservers(){
        signViewModel.signUpState.observe(this) {
            Timber.e(it.toString())
            startSignInActivity(it)
        }
    }

    private fun startSignInActivity(state: SignUpState) {
        when (state) {
            is SignUpState.Success -> {
                Toast.makeText(this, state.singUpResponse, Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
            is SignUpState.Error -> {
                Toast.makeText(this, "Wrong data entered", Toast.LENGTH_SHORT).show()
            }
            is SignUpState.RegisterCheck -> {
               if(state.found == 0) signViewModel.registerUser(username, password, firstname, lastname, country, phone)
               else Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
            }
                is SignUpState.DataFetched -> {
                Toast.makeText(this, "Fresh data fetched from the server", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}