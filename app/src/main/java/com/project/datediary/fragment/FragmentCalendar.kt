package com.project.datediary.fragment

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.datediary.databinding.FragmentCalendarBinding
import android.graphics.Point
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.project.datediary.R


class FragmentCalendar : Fragment() {

    lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)


        class MainActivity : AppCompatActivity() {

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)

                var todayList = binding.todayList
                var todayLayout = binding.todayLayout
                var calenderBody = binding.calenderBody

                todayList.setOnClickListener {
//            ObjectAnimator.ofFloat(todayLayout, "translationY", -500f).apply {
//                duration = 300
//                start()
//            }

                    val display = windowManager.defaultDisplay
                    val size = Point()
                    display.getSize(size)
                    val customH2 = size.y * 6 / 12

                    val params2 = calenderBody.getLayoutParams()
                    params2.height = customH2
                    calenderBody.setLayoutParams(params2)

                }

            }
        }

        return binding.root
    }

}
