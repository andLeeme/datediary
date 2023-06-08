package com.project.datediary.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.project.datediary.R
import com.project.datediary.adapter.ViewPager2Adapter
import com.project.datediary.databinding.ActivityMainBinding
import com.project.datediary.databinding.ActivityView2Binding
import com.project.datediary.databinding.FragmentStoryBinding
import com.project.datediary.fragment.FragmentViewPager1
import com.project.datediary.fragment.FragmentViewPager2
import com.project.datediary.fragment.FragmentViewPager3

class ViewActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityView2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityView2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
    }

    private fun initViewPager() {
        //ViewPager2 Adapter 셋팅
        var viewPager2Adatper = ViewPager2Adapter(this)
        viewPager2Adatper.addFragment(FragmentViewPager1())
        viewPager2Adatper.addFragment(FragmentViewPager2())
        viewPager2Adatper.addFragment(FragmentViewPager3())

        //Adapter 연결
        binding.vpViewpagerMain.apply {
            adapter = viewPager2Adatper

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.tlNavigationView, binding.vpViewpagerMain) { tab, position ->
            Log.e("YMC", "ViewPager position: $position")
            when (position) {
                0 -> tab.text = ""
                1 -> tab.text = ""
                2 -> tab.text = ""
            }
        }.attach()
    }
}
