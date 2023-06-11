package com.project.datediary.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.activity.MainActivity
import com.project.datediary.adapter.DayScheduleAdapter
import com.project.datediary.adapter.NoticeAdapter
import com.project.datediary.databinding.FragmentStoryBinding
import com.project.datediary.model.NoticeResponseBody
import com.project.datediary.model.TitleRequestBody
import com.project.datediary.model.TitleResponseBody
import com.project.datediary.util.CalendarUtil
import retrofit2.Call
import retrofit2.Response
import java.util.Date

class FragmentStory : Fragment() {

    lateinit var binding: FragmentStoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoryBinding.inflate(inflater, container, false)

        binding.recyclerViewNotice.layoutManager = LinearLayoutManager(requireContext())

        var NoticeResponseBody = ArrayList<NoticeResponseBody>()


        NoticeResponseBody.add(NoticeResponseBody("1",Date().toString(),"이수영","0"))
        NoticeResponseBody.add(NoticeResponseBody("1",Date().toString(),"김인호","1"))
        NoticeResponseBody.add(NoticeResponseBody("1",Date().toString(),"김인호","0"))
        NoticeResponseBody.add(NoticeResponseBody("1",Date().toString(),"이수영","1"))
        NoticeResponseBody.add(NoticeResponseBody("1",Date().toString(),"이수영","1"))
        NoticeResponseBody.add(NoticeResponseBody("1",Date().toString(),"이수영","0"))
        NoticeResponseBody.add(NoticeResponseBody("1",Date().toString(),"김인호","1"))
        NoticeResponseBody.add(NoticeResponseBody("1",Date().toString(),"김인호","0"))
        NoticeResponseBody.add(NoticeResponseBody("1",Date().toString(),"이수영","1"))
        NoticeResponseBody.add(NoticeResponseBody("1",Date().toString(),"이수영","1"))

        //어댑터에 넣어주기
        val adapter = NoticeAdapter(NoticeResponseBody)


        //어댑터 적용
        binding.recyclerViewNotice.adapter = adapter


        return binding.root
    }
}
