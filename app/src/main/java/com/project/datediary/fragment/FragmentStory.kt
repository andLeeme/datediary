package com.project.datediary.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.datediary.R
import com.project.datediary.databinding.FragmentStoryBinding

class FragmentStory : Fragment() {

    lateinit var binding: FragmentStoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoryBinding.inflate(inflater, container, false)

//        Glide.with(this)
//            .load(R.drawable.icon_story)
//            .into(binding.imageview)


        binding.button1.setOnLongClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.editContainer, FragmentStoryEdit())
                .commit()
            return@setOnLongClickListener(true)
        }

//        binding.button1.setOnClickListener {
//
//            Glide.with(this)
//                .load(R.drawable.icon_story)
//                .into(binding.imageview)
//
//        }

        return binding.root
    }



}