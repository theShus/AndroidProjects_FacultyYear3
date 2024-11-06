package com.example.domaci1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button loginBtn;
    Button registerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initButtons();
    }

    private void init(){

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(Register.REG_NAME_KEY, "");
        String password = sharedPreferences.getString(Register.REG_PASS_KEY, "");

        System.out.println( username + " - " + password);

        if (username.equals("") & password.equals("")){
            Intent regIntent = new Intent(this, Register.class);
            startActivity(regIntent);
        }
        else {
            Intent logIntent = new Intent(this, Login.class);
            startActivity(logIntent);
        }
    }

    private void initButtons(){

        loginBtn = findViewById(R.id.mainLoginBTN);
        registerBtn = findViewById(R.id.mainRegisterBTN);

        loginBtn.setOnClickListener(v -> {
            Intent logIntent = new Intent(this, Login.class);//da li ima nacin da se udje na stari ili moram da svaku put pravim novi intent?
            startActivity(logIntent);//da li da se napravi globalni intent??
        });

        registerBtn.setOnClickListener(v -> {
            Intent regIntent = new Intent(this, Register.class);
            startActivity(regIntent);
        });


    }
}