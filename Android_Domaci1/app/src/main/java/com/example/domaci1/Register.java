package com.example.domaci1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText username;
    EditText password;
    Button registerBtn;

    public static final String REG_NAME_KEY = "usernameKey";
    public static final String REG_PASS_KEY = "passwordKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init(){
        initview();
        initListeners();
    }

    private void initview(){
        username = findViewById(R.id.regUser);
        password = findViewById(R.id.regPass);
        registerBtn = findViewById(R.id.regbtn);
    }

    private void initListeners(){

        registerBtn.setOnClickListener(v ->{

            SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            sharedPreferences
                    .edit()
                    .putString(REG_NAME_KEY, username.getText().toString().trim())
                    .putString(REG_PASS_KEY, password.getText().toString().trim())
                    .apply();

            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();


//            Toast.makeText(this,username.getText().toString().trim() + " - " +  password.getText().toString().trim(), Toast.LENGTH_SHORT).show();
        });

    }


}