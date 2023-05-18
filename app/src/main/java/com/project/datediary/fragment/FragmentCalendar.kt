package com.project.datediary.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.datediary.databinding.FragmentCalendarBinding
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.JsonObject
import com.project.datediary.adapter.customAdapter
import com.project.datediary.model.ScheduleRequestBody
import com.project.datediary.model.ScheduleResponseBody
import com.project.datediary.model.dataVO
import retrofit2.Call
import retrofit2.Response


class FragmentCalendar : Fragment() {

    lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)


        var data1 = arrayListOf<dataVO>(
            dataVO("어디서1", "누구와", "어떻게", "무엇을"),
            dataVO("어디서2", "누구와", "어떻게", "무엇을"),
            dataVO("어디서3", "누구와", "어떻게", "무엇을"),
            dataVO("어디서4", "누구와", "어떻게", "무엇을"),
            dataVO("어디서5", "누구와", "어떻게", "무엇을"),
            dataVO("어디서6", "누구와", "어떻게", "무엇을"),
            dataVO("어디서7", "누구와", "어떻게", "무엇을"),
            dataVO("어디서8", "누구와", "어떻게", "무엇을"),
            dataVO("어디서9", "누구와", "어떻게", "무엇을"),
            dataVO("어디서10", "누구와", "어떻게", "무엇을")
        )
        Log.d("dataVO", "${data1.get(0).sche2}")

        val mAdapter = customAdapter(requireContext(), data1)
        binding.recyclerView.adapter = mAdapter

        val gridLayoutManager = GridLayoutManager(context, 7)
        binding.recyclerView.layoutManager = gridLayoutManager


        //로그인 한 유저인덱스로 바꿔주기
        val userData = ScheduleRequestBody(
            couple_index = "1"
        )
        Toast.makeText(context, "$userData", Toast.LENGTH_SHORT).show()

        RetrofitAPI.emgMedService2.addUserByEnqueue2(userData)
            .enqueue(object : retrofit2.Callback<ArrayList<ScheduleResponseBody>> {
                override fun onResponse(
                    call: Call<ArrayList<ScheduleResponseBody>>,
                    response: Response<ArrayList<ScheduleResponseBody>>
                ) {
                    if (response.isSuccessful) {

                        val list = response.body() ?: listOf()

                        val titleList = arrayListOf<String>()
                        val dayList = arrayListOf<String>()

                        for (i in list.indices) {
                            titleList.add(list[i].title.toString())
                            Log.d("size", "${titleList[i]}")
                        }
                    }

                }

                override fun onFailure(
                    call: Call<ArrayList<ScheduleResponseBody>>, t: Throwable
                ) {

                }
            })

        return binding.root

    }

}
