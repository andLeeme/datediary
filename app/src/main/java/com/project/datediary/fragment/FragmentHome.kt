package com.project.datediary.fragment

import android.R.attr.height
import android.R.attr.width
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import com.bumptech.glide.Glide
import com.project.datediary.R
import com.project.datediary.databinding.FragmentHomeBinding
import com.project.datediary.util.SetBackground


class FragmentHome : Fragment() {

    lateinit var binding: FragmentHomeBinding

    private var result_coupleIndex: String? = ""
    private var result_imageUrl: String? = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setBackground()

        binding.addMainImage.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.editContainerHome, UploadFragment())
                .commit()
            binding.fragmentHome.visibility = View.INVISIBLE

        }


        childFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner,
            FragmentResultListener { key, bundle ->

                result_coupleIndex = bundle.getString("coupleIndex")
                result_imageUrl = bundle.getString("imageUrl")

                Toast.makeText(requireContext(), "$result_coupleIndex", Toast.LENGTH_SHORT).show()
                Toast.makeText(requireContext(), "$result_imageUrl", Toast.LENGTH_SHORT).show()
                Log.d("result_imageUrl", "onCreateViewPC: $result_coupleIndex")
                Log.d("result_imageUrl", "onCreateViewP: $result_imageUrl")

                SetBackground.backgroundURI  = result_imageUrl

                if (result_imageUrl != "") {
                    setBackground()
                }
            })


        return binding.root
    }

    fun setBackground() {

        Glide
            .with(binding.root)
            .load(SetBackground.backgroundURI)
            .centerCrop()
            .into(binding.backgroundHome)

        binding.fragmentHome.visibility = View.VISIBLE
    }
}










