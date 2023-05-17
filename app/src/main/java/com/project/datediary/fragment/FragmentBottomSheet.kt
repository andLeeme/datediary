package com.project.datediary.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.datediary.AddScheduleActivity
import com.project.datediary.databinding.FragmentBottomSheetBinding

class FragmentBottomSheet : Fragment() {

    lateinit var binding: FragmentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {


        binding.addBtn.setOnClickListener {
            startActivity(Intent(requireActivity(), AddScheduleActivity::class.java))

        }
        return binding.root
    }

}