package com.project.datediary.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.R
import com.project.datediary.fragment.FragmentCalendar
import com.project.datediary.model.dataVO

class customAdapter(private val context: Context, private val dataList: ArrayList<dataVO>) :
    RecyclerView.Adapter<customAdapter.ItemViewHolder>() {
    var mPosition = 0
    fun getPosition(): Int {
        return mPosition
    }

    private fun setPosition(position: Int) {
        mPosition = position
    }

    fun addItem(dataVO: dataVO) {
        dataList.add(dataVO)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        if (position > 0) {
            dataList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    inner class ItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val sche11 = itemView.findViewById<TextView>(R.id.schedule1)
        private val sche12 = itemView.findViewById<TextView>(R.id.schedule2)
        private val sche13 = itemView.findViewById<TextView>(R.id.schedule3)
        private val sche14 = itemView.findViewById<TextView>(R.id.schedule4)

        fun bind(data1: dataVO, context: Context) {
                Toast.makeText(context, "데이터 들어는간다", Toast.LENGTH_SHORT).show()
                sche11.text = data1.sche1
                sche12.text = data1.sche2
                sche13.text = data1.sche3
                sche14.text = data1.sche4

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_grid, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position], context)

        holder.itemView.setOnClickListener { view->
            setPosition(position)
            Toast.makeText(view.context, "$position 아이템 클릭", Toast.LENGTH_SHORT).show()


        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}