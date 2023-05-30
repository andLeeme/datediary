package com.project.datediary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.databinding.ChkscheduleBinding
import com.project.datediary.model.ScheduleShowResponseBody

class DayScheduleAdapter : RecyclerView.Adapter<DayScheduleAdapter.MyView>() {

    private var scheduleShowList = listOf<ScheduleShowResponseBody>()

    inner class MyView(private val binding: ChkscheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.scheduleIndex.text = scheduleShowList[pos].schedule_index
            binding.title.text = scheduleShowList[pos].title
            binding.startTime.text = scheduleShowList[pos].start_time
            binding.endTime.text = scheduleShowList[pos].end_time
            binding.contents.text = scheduleShowList[pos].contents
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

    fun setList(list: ArrayList<ScheduleShowResponseBody>) {
        scheduleShowList = list
    }
}