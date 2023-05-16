package com.project.datediary.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.datediary.R
import com.project.datediary.databinding.FragmentBottomSheetBinding

class FragmentBottomSheet : Fragment() {

    lateinit var binding: FragmentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        binding.addByn.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.story_frm, FragmentStoryEdit())
                .commit()
        }


        return binding.root


    }
}
