package com.project.datediary.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.datediary.R
import com.project.datediary.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {

    lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.addMainImage.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.editContainerHome, UploadFragment())
                .commit()
            binding.fragmentHome.visibility = View.INVISIBLE
        }

        return binding.root
    }

}










