package com.project.datediary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.datediary.Fragment.FragmentCalendar
import com.project.datediary.Fragment.FragmentGraph
import com.project.datediary.Fragment.FragmentHome
import com.project.datediary.Fragment.FragmentMyPage
import com.project.datediary.Fragment.FragmentStory
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