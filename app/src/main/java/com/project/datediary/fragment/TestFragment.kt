package com.project.datediary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.datediary.databinding.FragmentStoryEditBinding
import com.project.datediary.databinding.FragmentTestBinding



class TestFragment : Fragment() {

    lateinit var binding: FragmentTestBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestBinding.inflate(inflater, container, false)


        return binding.root
    }


}