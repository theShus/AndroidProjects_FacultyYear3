package com.example.projekat1.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projekat1.R;
import com.example.projekat1.activities.MainActivity;

public class ProfileFragment extends Fragment {

    private TextView username;
    private TextView email;
    private Button logout;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view){
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(), Context.MODE_PRIVATE);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

        username = view.findViewById(R.id.usernameTV);
        email = view.findViewById(R.id.emailTV);
        logout = view.findViewById(R.id.logOutButton);
        username.setText(sharedPreferences.getString(MainActivity.LOGGED_USER,""));
        email.setText(sharedPreferences.getString(MainActivity.LOGGED_MAIL,""));

        logout.setOnClickListener(v -> {
            MainActivity.refill = true;
            sharedPreferences
                    .edit()
                    .putString(MainActivity.LOGGED_USER, "replace")
                    .apply();

            transaction.replace(R.id.mainFragContainer, new LogInFragment());
            transaction.commit();
        });
    }
}
