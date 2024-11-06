package com.example.projekat_septembar.presentation.view.recycler.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.projekat_septembar.presentation.view.fragment.NewestFragment
import com.example.projekat_septembar.presentation.view.fragment.SavedFragment
import com.example.projekat_septembar.presentation.view.fragment.SearchFragment


class BottomNavPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        const val FRAG_1_NEWEST = 0
        const val FRAG_2_SEARCH = 1
        const val FRAG_3_SAVED = 2
        const val ITEM_COUNT = 3
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getItem(position: Int): Fragment {

        val fragment: Fragment = when (position) {
            FRAG_1_NEWEST -> NewestFragment()
            FRAG_2_SEARCH -> SearchFragment()
            FRAG_3_SAVED -> SavedFragment()
            else -> throw IllegalStateException("Unexpected value: $position")
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            FRAG_1_NEWEST -> "Discover"
            FRAG_2_SEARCH -> "Portfolio"
            FRAG_3_SAVED -> "Profile"
            else -> "error"
        }
    }
}