package com.example.travelapp

import Screens.Registration.ThirdAdd.DayMenuFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MenuAddSwipeAdapter(fragment: FragmentActivity, days: Int) : FragmentStateAdapter(fragment) {

    val daysAmount = days

    val list = mutableListOf<DayMenuFragment>(
        //DayMenuFragment(1),
        //DayMenuFragment(2)
    )

    override fun getItemCount(): Int = daysAmount

    override fun createFragment(position: Int): Fragment {

        val page = DayMenuFragment(position + 1)
        list.add(page)
        return list.get(position)
    }
}