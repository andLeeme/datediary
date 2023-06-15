package com.project.datediary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.databinding.NoticeItemBinding
import com.project.datediary.model.NoticeResponseBody

class NoticeAdapter(private val noticeList: ArrayList<NoticeResponseBody>) :
    RecyclerView.Adapter<NoticeAdapter.MyView>() {

    inner class MyView(private val binding: NoticeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {

            Log.d("NoticeAdapter2", "$noticeList")


            //타입에 따라 아이템에 추가
            when (noticeList[pos].type) {
                "0" -> {
                    binding.name.text = noticeList[pos].name
                    binding.date.text = noticeList[pos].month+"월"+noticeList[pos].day+"일"
                    binding.text.text = " 일정을 등록했습니다"
                }
                "1" -> {
                    binding.name.text = noticeList[pos].name
                    binding.date.text = noticeList[pos].month+"월"+noticeList[pos].day+"일"
                    binding.text.text = " 일정을 수정했습니다"
                }
                "2" -> {
                    binding.name.text = noticeList[pos].name
                    binding.date.text = "배경화면"
                    binding.text.text = "을 변경했습니다"

                }
            }

            binding.time.text = noticeList[pos].timestamp

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