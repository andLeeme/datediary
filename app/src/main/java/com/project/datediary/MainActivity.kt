package com.project.datediary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.datediary.fragment.FragmentCalendar
import com.project.datediary.fragment.FragmentGraph
import com.project.datediary.fragment.FragmentHome
import com.project.datediary.fragment.FragmentMyPage
import com.project.datediary.fragment.FragmentStory
import com.project.datediary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }


    private fun initBottomNavigation() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, FragmentHome())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentHome())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.story -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentStory())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.calendar -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentCalendar())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.graph -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentGraph())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.myPage -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentMyPage())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }
}