package com.project.datediary.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.datediary.databinding.FragmentCalendarBinding
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.project.datediary.R
import com.project.datediary.adapter.customAdapter
import com.project.datediary.model.dataVO
import kotlin.math.log


class FragmentCalendar : Fragment() {

    lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)




        var data1 = arrayListOf<dataVO>(
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("내용1입니다", "내용2입니다", "내용3입니다", "내용4입니다"),
            dataVO("내용1입니다", "", "", "내용4입니다"),
            dataVO("", "내용2입니다", "", "내용4입니다"),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("내용1입니다", "내용2입니다", "내용3입니다", ""),
            dataVO("어디서1", "누구와", "어떻게", "무엇을"),
            dataVO("", "", "", ""),
            dataVO("", "", "내용3입니다", ""),
            dataVO("", "", "", ""),
            dataVO("어디서5", "누구와", "어떻게", "무엇을"),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "내용3입니다", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "내용2입니다", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("내용1입니다", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),
            dataVO("", "", "", ""),


        )
        Log.d("dataVO", "${data1.get(0).sche2}")

        val mAdapter = customAdapter(requireContext(), data1)
        binding.recyclerView.adapter = mAdapter

        val gridLayoutManager = GridLayoutManager(context, 7)
        binding.recyclerView.layoutManager = gridLayoutManager

        return binding.root
    }

}
