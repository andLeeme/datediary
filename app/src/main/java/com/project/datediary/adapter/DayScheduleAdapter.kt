package com.project.datediary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.databinding.ChkscheduleBinding
import com.project.datediary.model.ScheduleShowResponseBody
import com.project.datediary.model.TitleResponseBody

class DayScheduleAdapter(private val scheduleShowList : ArrayList<TitleResponseBody>) : RecyclerView.Adapter<DayScheduleAdapter.MyView>() {

    inner class MyView(private val binding: ChkscheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {

            binding.scheduleIndex.text = scheduleShowList[pos].scheduleIndex
            binding.title.text = scheduleShowList[pos].title
            binding.startTime.text = scheduleShowList[pos].startTime
            binding.endTime.text = scheduleShowList[pos].endTime

            if(scheduleShowList[pos].contents =="") {
                binding.contents.text = scheduleShowList[pos].title
            } else {
                binding.contents.text = scheduleShowList[pos].contents
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = ChkscheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyView(view)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return scheduleShowList.size
    }

//    fun setList(list: ArrayList<TitleResponseBody>) {
//        scheduleShowList = list
//        Log.d("scheduleShowList", "bind: $scheduleShowList")
//    }
}