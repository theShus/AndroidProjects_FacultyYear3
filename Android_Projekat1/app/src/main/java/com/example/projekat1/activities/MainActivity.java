package com.example.projekat1.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentTransaction;

import com.example.projekat1.R;
import com.example.projekat1.fragments.BottomNavFragment;
import com.example.projekat1.fragments.LogInFragment;
import com.example.projekat1.models.User;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public static final String LOGGED_USER = "loggedusername";
    public static final String LOGGED_MAIL = "loggedmail";
    public static final String TODO = "todo";
    public static final String IN_PROGRESS = "inprogress";
    public static final String DONE = "done";
    public static final String MAIN_FRAGMENT = "mainFragment";
    public static boolean refill = true;
    private SharedPreferences sharedPreferences;
    private boolean loggedIn;
    private String logged;
    public static final Map<String, User> users = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        users.put("admin_marko", new User("admin_marko","1234","marko@gmail.com"));
        users.put("luka", new User("luka","1234","luka@gmail.com"));
        users.put("marko", new User("marko","1234","marko@gmail.com"));

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> {
            setContentView(R.layout.activity_main);
            logged = sharedPreferences.getString(LOGGED_USER, "");
            loggedIn = !logged.equals("") && !logged.equals("replace");
            checkLogin();
            return false;
        });
    }

    private void checkLogin(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (loggedIn){
            transaction.add(R.id.mainFragContainer, new BottomNavFragment()/*, MAIN_FRAGMENT*/);
        }
        else {
            transaction.add(R.id.mainFragContainer, new LogInFragment());
        }
        transaction.commit();
    }
}