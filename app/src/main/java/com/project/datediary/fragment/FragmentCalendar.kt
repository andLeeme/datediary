package com.project.datediary.fragment

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.datediary.databinding.FragmentCalendarBinding
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.project.datediary.adapter.GridAdapter
import com.project.datediary.api.ApiObject
import com.project.datediary.databinding.FragmentCalendar2Binding
import com.project.datediary.model.Schedule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentCalendar : Fragment() {

    lateinit var binding: FragmentCalendar2Binding
    lateinit var gridAdapter: GridAdapter
    var coinList = listOf<Schedule>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendar2Binding.inflate(inflater, container, false)

        gridAdapter = GridAdapter()

//        class MainActivity : AppCompatActivity() {
//            override fun onCreate(savedInstanceState: Bundle?) {
//                super.onCreate(savedInstanceState)
//                setContentView(R.layout.activity_main)

//                var todayList = binding.todayList
//                var todayLayout = binding.todayLayout
//                var calenderBody = binding.calenderBody
//
//                todayList.setOnClickListener {
////            ObjectAnimator.ofFloat(todayLayout, "translationY", -500f).apply {
////                duration = 300
////                start()
////            }
//
//                    val display = windowManager.defaultDisplay
//                    val size = Point()
//                    display.getSize(size)
//                    val customH2 = size.y * 6 / 12
//
//                    val params2 = calenderBody.getLayoutParams()
//                    params2.height = customH2
//                    calenderBody.setLayoutParams(params2)
//
//                }

        gridAdapter.setList(coinList)
        gridAdapter.notifyDataSetChanged()

        binding.calenderBody2.apply {
            adapter = gridAdapter
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
        }
        initList()
        return binding.root
    }

    private fun initList() {
        val call = ApiObject.getRetrofitService.getCoinAll()
        call.enqueue(object : Callback<List<Schedule>> {
            override fun onResponse(call: Call<List<Schedule>>, response: Response<List<Schedule>>) {
                Toast.makeText(context, "Call Success", Toast.LENGTH_SHORT).show()
                if (response.isSuccessful) {
                    coinList = response.body() ?: listOf()
                    gridAdapter.setList(coinList)
                }
            }

            override fun onFailure(call: Call<List<Schedule>>, t: Throwable) {
                Toast.makeText(context, "Call Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
