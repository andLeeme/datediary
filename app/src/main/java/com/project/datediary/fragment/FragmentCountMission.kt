package com.project.datediary.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.datediary.databinding.FragmentCountMissionBinding


class FragmentCountMission : Fragment() {

    lateinit var binding: FragmentCountMissionBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountMissionBinding.inflate(inflater, container, false)


        return binding.root
    }
}

