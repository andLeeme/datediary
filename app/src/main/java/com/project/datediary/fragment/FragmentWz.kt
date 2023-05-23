package com.project.datediary.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.datediary.databinding.FragmentWzBinding

class FragmentWz : Fragment() {

    lateinit var binding: FragmentWzBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWzBinding.inflate(inflater, container, false)



        return binding.root
    }
}










