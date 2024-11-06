package com.example.projekat_septembar.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projekat_septembar.databinding.ActivitySignInBinding
import com.example.projekat_septembar.presentation.contract.SignContract
import com.example.projekat_septembar.presentation.view.states.SignInState
import com.example.projekat_septembar.presentation.viewmodel.SignViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class SignInActivity : AppCompatActivity() {

    private val signViewModel: SignContract.SignViewModel by viewModel<SignViewModel>()
    private lateinit var binding: ActivitySignInBinding

    private var username: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        initView()
        initObservers()
    }

    private fun initView(){
        binding.btnSignIn.setOnClickListener{
            username = binding.inputUsername.text.toString()
            password = binding.inputPassword.text.toString()

            if(username.isBlank() || password.isBlank()){
                Toast.makeText(applicationContext,"All fields must be filled", Toast.LENGTH_SHORT).show()
            }else {
                signViewModel.checkSignIn(username, password)
            }
        }

        binding.btnSignUpTransfer.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initObservers(){
        signViewModel.signInState.observe(this) {
            Timber.e(it.toString())
            startMainActivity(it)
        }
    }

    private fun startMainActivity(state: SignInState) {
        when (state) {
            is SignInState.Success -> {
                Toast.makeText(this, "Successfully signed in", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)//todo popravi da se pojavi splash
                startActivity(intent)
                finish()
            }
            is SignInState.Error -> {
                Toast.makeText(this, "Wrong data entered", Toast.LENGTH_SHORT).show()
            }
            is SignInState.Existing -> {
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is SignInState.DataFetched -> {
                Toast.makeText(this, "Fresh data fetched from the server", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

