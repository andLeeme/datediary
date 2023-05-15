package com.project.datediary.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.ItemTouchHelperCallback
import com.project.datediary.LinearListViewAdapter
import com.project.datediary.R

import com.project.datediary.databinding.FragmentMyBinding

class FragmentMyPage : Fragment() {

    lateinit var binding: FragmentMyBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerView
        val list = mutableListOf("category1", "category2", "category3", "category4", "category5", "category5", "category5", "category5", "category5", "category5", "category5", "category5", "category5", "category5", "category5", "category5", "category5", "category5", "category5")

        val adapter = LinearListViewAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val callback = ItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter = adapter
        adapter.startDrag(object : LinearListViewAdapter.OnStartDragListener {
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                touchHelper.startDrag(viewHolder)
            }
        })



        return binding.root
    }

}
