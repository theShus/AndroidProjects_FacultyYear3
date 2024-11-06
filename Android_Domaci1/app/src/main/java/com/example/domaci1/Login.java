package com.example.domaci1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Scene;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginBtn;
    Button clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        initview();
        initListeners();
    }

    private void initview(){
        username = findViewById(R.id.logUser);
        password = findViewById(R.id.logPass);
        loginBtn = findViewById(R.id.loginbtn);
        clearBtn = findViewById(R.id.clearReg);
    }
    private void initListeners(){
        loginBtn.setOnClickListener(v ->{
            SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            String storedUsername = sharedPreferences.getString(Register.REG_NAME_KEY, "");
            String storedPassword = sharedPreferences.getString(Register.REG_PASS_KEY, "");

            if(storedUsername.equals(username.getText().toString().trim()) &&
                 storedPassword.equals(password.getText().toString().trim())){
                Intent intent = new Intent(this, Screen.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Incorrect username or password!", Toast.LENGTH_SHORT).show();
            }

        });

        clearBtn.setOnClickListener(v ->{
            SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            sharedPreferences.edit().clear().apply();
            Toast.makeText(this, "Shared preferences successfully cleared", Toast.LENGTH_SHORT).show();
        });



    }



}