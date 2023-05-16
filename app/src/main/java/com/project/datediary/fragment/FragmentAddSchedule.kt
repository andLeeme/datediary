package com.project.datediary.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.datediary.adapter.ArticleAdapter
import com.project.datediary.databinding.FragmentAddScheduleBinding
import com.project.datediary.model.ArticleModel
import com.project.datediary.databinding.FragmentHomeBinding

class FragmentAddSchedule : Fragment() {

    lateinit var binding: FragmentAddScheduleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddScheduleBinding.inflate(inflater, container, false)


        return binding.root
    }

}
