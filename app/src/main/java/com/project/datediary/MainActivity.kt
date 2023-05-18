package com.project.datediary

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.datediary.fragment.FragmentCalendar
import com.project.datediary.fragment.FragmentGraph
import com.project.datediary.fragment.FragmentHome
import com.project.datediary.fragment.FragmentMyPage
import com.project.datediary.fragment.FragmentStory
import com.project.datediary.databinding.ActivityMainBinding
import com.project.datediary.databinding.FragmentBottomSheetBinding
import com.project.datediary.fragment.FragmentBottomSheet

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.addBtn.setOnClickListener {
            Toast.makeText(this, "이건 될듯", Toast.LENGTH_SHORT).show()
        }

        binding.sheetText1.setOnClickListener {
            if (binding.sheetText1.text.toString() == "바뀐 일정") {
                binding.sheetText1.text = "오늘의 일정이에요"
            } else {
                binding.sheetText1.text = "바뀐 일정"
            Toast.makeText(this, "내용 바뀜", Toast.LENGTH_SHORT).show()
            }
            return@setOnClickListener
        }


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