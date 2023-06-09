package com.project.datediary.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.datediary.activity.MainActivity
import com.project.datediary.adapter.DayScheduleAdapter
import com.project.datediary.adapter.NoticeAdapter
import com.project.datediary.databinding.FragmentStoryBinding
import com.project.datediary.model.ChatRequestBody
import com.project.datediary.model.NoticeResponseBody
import com.project.datediary.model.TitleResponseBody
import com.project.datediary.util.CalendarUtil
import retrofit2.Call
import retrofit2.Response

class FragmentStory : Fragment() {

    lateinit var binding: FragmentStoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoryBinding.inflate(inflater, container, false)


        //스테이터스 바 색상 변경
        val activity = requireActivity()
        val window = activity.window
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR


        binding.recyclerViewNotice.layoutManager = LinearLayoutManager(requireContext())

        var NoticeResponseBody = ArrayList<NoticeResponseBody>()


        var chatRequestBody = ChatRequestBody(
            couple_index = MainActivity.coupleIndex,
            email = MainActivity.googleEmail,
            sender = MainActivity.nickname1,
        )


        //알림기능
        var noticeResponseBodyList = listOf<NoticeResponseBody>()

        RetrofitAPI.emgMedService12.addUserByEnqueue(chatRequestBody)
            .enqueue(object : retrofit2.Callback<ArrayList<NoticeResponseBody>> {
                override fun onResponse(
                    call: Call<ArrayList<NoticeResponseBody>>,
                    response: Response<ArrayList<NoticeResponseBody>>
                ) {

                    if (response.isSuccessful) {

                        noticeResponseBodyList = response.body() ?: listOf()

                        var noticeList = noticeResponseBodyList

                        var noticeList2 = noticeList.reversed()

                        val adapter2 = NoticeAdapter(noticeList2 as ArrayList<NoticeResponseBody>)

                        binding.recyclerViewNotice.adapter = adapter2

                    }


                }

                override fun onFailure(
                    call: Call<ArrayList<NoticeResponseBody>>,
                    t: Throwable
                ) {
                    Toast.makeText(context, "Call Failed", Toast.LENGTH_SHORT).show()
                }
            })


//        NoticeResponseBody.add(NoticeResponseBody("1",Date().toString(),"이수영","0"))

        //어댑터에 넣어주기
        val adapter = NoticeAdapter(NoticeResponseBody)


        //어댑터 적용
        binding.recyclerViewNotice.adapter = adapter


        return binding.root
    }
}
