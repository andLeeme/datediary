package com.project.datediary.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.datediary.R
import com.project.datediary.databinding.FragmentHomeBinding
import com.project.datediary.databinding.FragmentPlaceBinding

class FragmentPlace : Fragment() {

    lateinit var binding: FragmentPlaceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceBinding.inflate(inflater, container, false)



        return binding.root
    }
}


