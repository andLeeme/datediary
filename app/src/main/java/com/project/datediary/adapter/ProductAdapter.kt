package com.project.datediary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.databinding.ItemListBinding
import com.project.datediary.model.Schedule

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.MyView>() {
    private var Schedule = listOf<Schedule>()

    inner class MyView(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.text01.text = Schedule[pos].couple_index
            binding.text02.text = Schedule[pos].title
            binding.text03.text = Schedule[pos].contents
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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