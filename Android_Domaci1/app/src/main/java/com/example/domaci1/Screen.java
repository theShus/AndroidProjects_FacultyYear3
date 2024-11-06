package com.example.domaci1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Screen extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        initText();
    }

    private void initText(){
        text = findViewById(R.id.welcomeText);

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(Register.REG_NAME_KEY, "");

        text.setText("Welcome " + username);
    }
}