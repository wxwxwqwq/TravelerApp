package com.example.travelapp

import Screens.Registration.FirstAdd.FirstAddFragment
import Screens.Registration.SecondAdd.SecondAddFragment
import Screens.Registration.ThirdAdd.ThirdAddFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class RegistrationSwipeAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    val list = mutableListOf(
        FirstAddFragment(),
        SecondAddFragment(),
        ThirdAddFragment(),
    )

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return list.get(position)
    }


}