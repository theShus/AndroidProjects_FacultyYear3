package com.example.projekat1.viewPagers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.projekat1.fragments.tabs.DoneFragment;
import com.example.projekat1.fragments.tabs.InProgessFragment;
import com.example.projekat1.fragments.tabs.ToDoFragment;

import org.jetbrains.annotations.Nullable;


public class TabPagerAdapter extends FragmentPagerAdapter {

    public static final int FRAG_STAT_0 = 0;
    public static final int FRAG_NEW_1 = 1;
    public static final int FRAG_LIST_2 = 2;

    public TabPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case FRAG_STAT_0: fragment = new ToDoFragment();
//                System.out.println("TAB 0");
            break;
            case FRAG_NEW_1: fragment = new InProgessFragment();
//                System.out.println("TAB 1");
            break;
            default: fragment = new DoneFragment();
//                System.out.println("TAB 2");
            break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case FRAG_STAT_0: return "Todo";
            case FRAG_NEW_1: return "In Progress";
            default: return "Done";
        }
    }
}
