package com.project.datediary.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.datediary.databinding.FragmentBottomSheetBinding

class FragmentBottomSheet : Fragment() {

    lateinit var binding: FragmentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        binding.sheetText1.setOnClickListener {
            binding.sheetText1.text = "바뀐 일정"
        }

        return binding.root
    }

}