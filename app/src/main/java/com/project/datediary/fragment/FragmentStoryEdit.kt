package com.project.datediary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.datediary.databinding.FragmentStoryEditBinding

class FragmentStoryEdit : Fragment() {

    lateinit var binding: FragmentStoryEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoryEditBinding.inflate(inflater, container, false)

        //편집기능 넣기

        return binding.root
    }

}
