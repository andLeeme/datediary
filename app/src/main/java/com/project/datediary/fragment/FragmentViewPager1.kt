package com.project.datediary.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.datediary.databinding.FragmentViewPager1Binding


// Tab1Fragment.kt
class FragmentViewPager1 : Fragment() {
    lateinit var binding: FragmentViewPager1Binding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        binding = FragmentViewPager1Binding.inflate(inflater, container, false)

        val fadeOut1 = ObjectAnimator.ofFloat(binding.contain1, "alpha", 0f, 1f)
        fadeOut1.duration = 500
        fadeOut1.start()

        val fadeOut2 = ObjectAnimator.ofFloat(binding.contain2, "alpha", 0f, 1f)
        fadeOut2.duration = 500
        fadeOut2.start()

        val fadeOut3 = ObjectAnimator.ofFloat(binding.contain3, "alpha", 0f, 1f)
        fadeOut3.duration = 500
        fadeOut3.start()

        val fadeOut4 = ObjectAnimator.ofFloat(binding.contain4, "alpha", 0f, 1f)
        fadeOut4.duration = 500
        fadeOut4.start()

        val fadeOut5 = ObjectAnimator.ofFloat(binding.contain5, "alpha", 0f, 1f)
        fadeOut5.duration = 500
        fadeOut5.start()




        return binding.root
    }
}