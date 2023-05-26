package com.project.datediary.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.datediary.activity.AddScheduleActivity2
import com.project.datediary.R
import com.project.datediary.databinding.ActivityMainBinding
import com.project.datediary.databinding.FragmentStoryBinding
import com.project.datediary.model.Constants
import com.project.datediary.model.Employee
import com.project.datediary.util.DialogList

class FragmentStory : Fragment() {

    lateinit var binding: FragmentStoryBinding
    lateinit var bindingMain: ActivityMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoryBinding.inflate(inflater, container, false)

        bindingMain = ActivityMainBinding.inflate(inflater, container, false)


        binding.button1.setOnClickListener {
            val intent = Intent(requireActivity(), AddScheduleActivity2::class.java)
            startActivity(intent)
        }
        binding.button2.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.editContainer, FragmentWz())
                .commit()
            binding.fragmentStory.visibility = View.INVISIBLE

        }
        binding.button3.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.editContainer, FragmentStaggeredGrid())
                .commit()
            binding.fragmentStory.visibility = View.INVISIBLE

        }


        return binding.root
    }
}