package com.project.datediary.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.datediary.databinding.FragmentViewPager3Binding


// Tab1Fragment.kt
class FragmentViewPager3 : Fragment() {
    lateinit var binding: FragmentViewPager3Binding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        binding = FragmentViewPager3Binding.inflate(inflater, container, false)

        val fadeOut1 = ObjectAnimator.ofFloat(binding.contain1, "alpha", 0f, 3f)
        fadeOut1.duration = 1000
        fadeOut1.start()

        val fadeOut2 = ObjectAnimator.ofFloat(binding.contain2, "alpha", 0f, 3f)
        fadeOut2.duration = 2000
        fadeOut2.start()

        val fadeOut3 = ObjectAnimator.ofFloat(binding.contain3, "alpha", 0f, 3f)
        fadeOut3.duration = 3000
        fadeOut3.start()

        val fadeOut4 = ObjectAnimator.ofFloat(binding.contain4, "alpha", 0f, 3f)
        fadeOut4.duration = 4000
        fadeOut4.start()

        val fadeOut5 = ObjectAnimator.ofFloat(binding.contain5, "alpha", 0f, 3f)
        fadeOut5.duration = 5000
        fadeOut5.start()




        return binding.root
    }
}