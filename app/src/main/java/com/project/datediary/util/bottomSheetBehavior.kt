package com.project.datediary.util

import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.project.datediary.databinding.ActivityMainBinding
import java.util.Objects

interface bottomSheetBehavior {
    companion object {

        lateinit var binding: ActivityMainBinding
        var btn1 = binding.image1

        //bottomSheetBehavior 객체 생성
        //var bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        public fun bottomSheetUP() {
            lateinit var binding: ActivityMainBinding
            btn1.performClick()
            Log.d("test", "bottomSheetUP: test")

//            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
//            Log.d(
//                "DateFromUtil",
//                "onCreate: ${CalendarUtil.sMonth}.${CalendarUtil.sDay}.${CalendarUtil.sYear}"
//            )
        }
    }
}