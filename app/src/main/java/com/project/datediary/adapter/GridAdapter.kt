package com.project.datediary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.databinding.ListGridBinding
import com.project.datediary.model.Schedule
import java.util.*

class GridAdapter: RecyclerView.Adapter<GridAdapter.MyView>() {
    private var Schedule = listOf<Schedule>()

    inner class MyView(private val binding: ListGridBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.schedule1.text = Schedule[pos].title
            binding.schedule2.text = Schedule[pos].title
            binding.schedule3.text = Schedule[pos].title
            binding.schedule4.text = Schedule[pos].title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = ListGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyView(view)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return Schedule.size
    }

    fun setList(list: List<Schedule>) {
        Schedule = list
    }
}