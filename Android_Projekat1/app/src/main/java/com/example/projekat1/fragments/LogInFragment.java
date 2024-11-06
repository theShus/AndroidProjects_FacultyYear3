package com.example.projekat1.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projekat1.R;
import com.example.projekat1.activities.MainActivity;
import com.example.projekat1.models.User;

public class LogInFragment extends Fragment {

    private EditText username;
    private EditText password;
    private EditText email;
    private Button loginBtn;

    public LogInFragment() {
        super(R.layout.activity_login);
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
        username = (EditText) view.findViewById(R.id.logUsername);
        password = (EditText) view.findViewById(R.id.logPass);
        email = (EditText) view.findViewById(R.id.email);
        loginBtn = (Button) view.findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(v ->{
            String uname = username.getText().toString().trim();
            String umail = email.getText().toString();

            if(MainActivity.users.containsKey(uname)){//proverimo da li user uopte posoji
                User user = MainActivity.users.get(uname);
                if(user.getPassword().equals(password.getText().toString().trim()) && user.getEmail().equals(umail)){//proverimo da li matchuju podaci
                    SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(), Context.MODE_PRIVATE);

                    if(sharedPreferences.getString(MainActivity.LOGGED_USER,"").equals("replace"))
                        restartApp(view);

                    sharedPreferences//stavimo koji je user trenutno ulogovan
                            .edit()
                            .putString(MainActivity.LOGGED_USER, uname)
                            .putString(MainActivity.LOGGED_MAIL, umail)
                            .apply();

                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();//pokrenemo main fragment
                    transaction.replace(R.id.mainFragContainer, new BottomNavFragment());
                    transaction.commit();
                }
                else toastError("Credentials are incorrect");
            }
            else toastError("User not registered in database");
        });
    }

    private void toastError(String error){
        Toast.makeText(this.getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    private void restartApp(View view){
        Intent intent = new Intent(view.getContext(),  MainActivity.class);
        startActivity(intent);

        requireActivity().finish();
    }
}
