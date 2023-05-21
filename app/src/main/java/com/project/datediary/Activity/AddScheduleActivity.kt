package com.project.datediary.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.datediary.R
import com.project.datediary.databinding.ActivityAddScheduleBinding


class AddScheduleActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddScheduleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_schedule)

        binding = ActivityAddScheduleBinding.inflate(layoutInflater)

        setContentView(binding.root)


    }
}