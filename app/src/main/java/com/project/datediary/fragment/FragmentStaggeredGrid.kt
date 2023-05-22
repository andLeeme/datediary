package com.project.datediary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.project.datediary.adapter.MyAdapter
import com.project.datediary.databinding.FragmentStaggeredGridBinding
import com.project.datediary.databinding.FragmentStoryBinding
import java.util.Collections


class FragmentStaggeredGrid : Fragment() {

    lateinit var binding: FragmentStaggeredGridBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStaggeredGridBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerView


        val layoutManager = StaggeredGridLayoutManager(
            3, // 열의 수
            StaggeredGridLayoutManager.VERTICAL // 방향
        )
        recyclerView.layoutManager = layoutManager

        val itemList = mutableListOf<String>()

        for (i in 1..35) {
            (itemList as MutableList<String>).add("Item $i")
        }

        val adapter = MyAdapter(itemList)
        recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                // 아이템 위치 변경
                Collections.swap(itemList, fromPosition, toPosition)
                adapter.notifyItemMoved(fromPosition, toPosition)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // 스와이프 동작 처리
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)


        return binding.root
    }


}

