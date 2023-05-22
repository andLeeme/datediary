package com.project.datediary.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.adapter.GridViewAdapter

import com.project.datediary.databinding.FragmentWzBinding
import com.project.datediary.util.ItemTouchHelperCallback

class FragmentWz : Fragment() {

    lateinit var binding: FragmentWzBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GridViewAdapter
    private lateinit var itemList: List<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWzBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView
        itemList = mutableListOf()

        for (i in 1..35) {
            (itemList as MutableList<String>).add("Item $i")
        }

        adapter = GridViewAdapter(itemList as MutableList<String>)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 5)

//        val itemTouchHelperCallback = ItemTouchHelperCallback()
//        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
//        itemTouchHelper.attachToRecyclerView(recyclerView)

        return binding.root
    }
}










