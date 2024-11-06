package com.example.projekat1.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.projekat1.R;
import com.example.projekat1.viewPagers.BottomNavPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavFragment extends Fragment {

    private ViewPager viewPager;

    public BottomNavFragment() {
        super(R.layout.fragment_bottom_navigation);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @SuppressLint("NonConstantResourceId")
    private void init(View view){
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(12);
        viewPager.setAdapter(new BottomNavPagerAdapter(this.requireActivity().getSupportFragmentManager()));

        setBottomNavColor(view);

        ((BottomNavigationView) view.findViewById(R.id.bottomNavigation)).setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                // setCurrentItem metoda viewPager samo obavesti koji je Item trenutno aktivan i onda metoda getItem u adapteru setuje odredjeni fragment za tu poziciju
                case R.id.navigation_1: viewPager.setCurrentItem(BottomNavPagerAdapter.FRAG_STAT_0, false);
//                    System.out.println("BOTTOM NAVFRAG 1");
                break;
                case R.id.navigation_2: viewPager.setCurrentItem(BottomNavPagerAdapter.FRAG_NEW_1, false);
//                    System.out.println("BOTTOM NAVFRAG 2");
                    break;
                case R.id.navigation_3: viewPager.setCurrentItem(BottomNavPagerAdapter.FRAG_LIST_2, false);
//                    System.out.println("BOTTOM NAVFRAG 3");
                    break;
                case R.id.navigation_4: viewPager.setCurrentItem(BottomNavPagerAdapter.FRAG_PROFILE_3, false);
//                    System.out.println("BOTTOM NAVFRAG 4");
                    break;
            }
            return true;
        });
    }

    private void setBottomNavColor(View view){
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigation);
        if(!requireActivity().getTheme().equals("android.content.res.Resources$Theme@c92409f")){
            bottomNavigationView.setBackgroundColor(Color.GRAY);
        }
    }

}
