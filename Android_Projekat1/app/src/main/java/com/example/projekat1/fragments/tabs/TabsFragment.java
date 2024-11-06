package com.example.projekat1.fragments.tabs;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.projekat1.R;
import com.example.projekat1.viewPagers.TabPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class TabsFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    public TabsFragment() {
        super(R.layout.fragment_tabs);
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
        viewPager = (ViewPager) view.findViewById(R.id.viewPagerTabs);
        viewPager.setOffscreenPageLimit(10);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        viewPager.setAdapter(new TabPagerAdapter(this.requireActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }
}
