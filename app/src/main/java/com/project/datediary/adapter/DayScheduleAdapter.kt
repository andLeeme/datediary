package com.project.datediary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.databinding.ChkscheduleBinding
import com.project.datediary.model.ScheduleShowResponseBody
import com.project.datediary.model.TitleResponseBody

class DayScheduleAdapter(private val scheduleShowList : ArrayList<TitleResponseBody>) : RecyclerView.Adapter<DayScheduleAdapter.MyView>() {

    inner class MyView(private val binding: ChkscheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {

            Log.d("List12345", "bind: $scheduleShowList")

            binding.scheduleIndex.text = scheduleShowList[pos].scheduleIndex
            binding.title.text = scheduleShowList[pos].title


            if(scheduleShowList[pos].contents =="") {
                binding.contents.text = scheduleShowList[pos].title
            } else {
                binding.contents.text = scheduleShowList[pos].contents
            }


            if(scheduleShowList[pos].startTime == "") {
                binding.startTime.visibility = View.GONE
                binding.endTime.visibility = View.GONE
                binding.allDay.visibility = View.VISIBLE
            } else {
                binding.startTime.visibility = View.VISIBLE
                binding.endTime.visibility = View.VISIBLE
                binding.allDay.visibility = View.GONE
                binding.startTime.text = scheduleShowList[pos].startTime
                binding.endTime.text = scheduleShowList[pos].endTime
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = ChkscheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyView(view)
    }

    interface DayScheduleOnItemClickListener {
        fun dayScheduleOnClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun dayScheduleSetItemClickListener(dayScheduleOnItemClickListener: DayScheduleOnItemClickListener) {
        this.dayScheduleOnItemClickListener = dayScheduleOnItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var dayScheduleOnItemClickListener : DayScheduleOnItemClickListener



    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(position)

        holder.itemView.setOnClickListener {

            dayScheduleOnItemClickListener.dayScheduleOnClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return scheduleShowList.size
    }

}