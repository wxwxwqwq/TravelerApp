package com.example.travelapp

import Screens.Using.FirstUse.FirstUseFragment
import Screens.Using.FourthUse.FourthUseFragment
import Screens.Using.SecondUse.SecondUseFragment
import Screens.Using.ThirdUse.ThirdUseFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class UsingSwipeAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    val list = mutableListOf(
        FirstUseFragment(),
        SecondUseFragment(),
        ThirdUseFragment(),
    )

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return list.get(position)
    }
}