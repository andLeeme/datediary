package com.project.datediary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.databinding.ChkscheduleBinding
import com.project.datediary.databinding.NoticeItemBinding
import com.project.datediary.model.NoticeResponseBody
import com.project.datediary.model.TitleResponseBody

class NoticeAdapter(private val noticeList : ArrayList<NoticeResponseBody>) : RecyclerView.Adapter<NoticeAdapter.MyView>() {

    inner class MyView(private val binding: NoticeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {

            if(noticeList[pos].type == "0") {
                binding.contents.text = noticeList[pos].nickname+"님이 일정을 등록했습니다"
                   
            }else if(noticeList[pos].type == "1") {
                binding.contents.text = noticeList[pos].nickname+"님이 배경화면을 변경했습니다"
            }
            binding.time.text = noticeList[pos].time_stamp


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = NoticeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyView(view)
    }


    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(position)

        }

    override fun getItemCount(): Int {
        return noticeList.size
    }

}