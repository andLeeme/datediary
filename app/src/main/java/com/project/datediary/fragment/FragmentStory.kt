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
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.activity.MainActivity
import com.project.datediary.adapter.DayScheduleAdapter
import com.project.datediary.adapter.NoticeAdapter
import com.project.datediary.databinding.FragmentStoryBinding
import com.project.datediary.model.ChatRequestBody
import com.project.datediary.model.ChatResponseBody
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
            type = "1",
            timestamp = Date().toString()
        )

        RetrofitAPI.emgMedService12.addUserByEnqueue(chatRequestBody)
            .enqueue(object : retrofit2.Callback<ArrayList<ChatResponseBody>> {
                override fun onResponse(
                    call: Call<ArrayList<ChatResponseBody>>,
                    response: Response<ArrayList<ChatResponseBody>>
                ) {
                    Log.d("coupleIndex", "Call Success")

                    if (response.isSuccessful) {

//                        val adapter = NoticeAdapter(response.body())
                    }
                }

                override fun onFailure(
                    call: Call<ArrayList<ChatResponseBody>>,
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
