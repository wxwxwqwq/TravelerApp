package com.example.travelapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class UsingActivity : AppCompatActivity() {

    lateinit var BottomNav: BottomNavigationView

    lateinit var adapter: UsingSwipeAdapter
    lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_using)

        UsingActivity_context = this

        adapter = UsingSwipeAdapter(this)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = adapter


    }

    override fun onStart() {
        super.onStart()

        //Установка экрана ViewPager2 в зависимости от BottomNavigationView
        BottomNav = findViewById(R.id.bottomNavigationView)
        BottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.firstUseFragment -> {
                    viewPager.currentItem = 0
                }
                R.id.secondUseFragment -> {
                    viewPager.currentItem = 1
                }
                R.id.thirdUseFragment -> {
                    viewPager.currentItem = 2
                }
            }
            true
        }

        //Установка экрана BottomNavigationView в зависимости от ViewPager2
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when(position){
                    0 -> {
                        BottomNav.menu.findItem(R.id.firstUseFragment).isChecked = true
                    }
                    1 -> {
                        BottomNav.menu.findItem(R.id.secondUseFragment).isChecked = true
                    }
                    2 -> {
                        BottomNav.menu.findItem(R.id.thirdUseFragment).isChecked = true
                    }
                }
            }
        })


        val ExitBtn: Button = findViewById(R.id.ExitBtn)
        ExitBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}