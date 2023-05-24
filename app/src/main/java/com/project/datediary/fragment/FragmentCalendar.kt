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
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.project.datediary.adapter.CalendarAdapter
import com.project.datediary.adapter.customAdapter
import com.project.datediary.model.ScheduleRequestBody
import com.project.datediary.model.ScheduleResponseBody
import com.project.datediary.model.SignUpRequestBody
import com.project.datediary.model.TitleRequestBody
import com.project.datediary.model.TitleResponseBody
import com.project.datediary.model.dataVO
import com.project.datediary.util.CalendarUtil
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date


class FragmentCalendar : Fragment() {

    lateinit var binding: FragmentCalendarBinding
    var month_view = Int

    //lateinit var calendar: Calendar

    //년월 변수
    //lateinit var selectedDate: LocalDate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        //현재 날짜
        //selectedDate  = LocalDate.now()
        //CalendarUtil.selectedDate  = LocalDate.now() //Util 만들어준 후 이렇게 씀

        //초기화
        //calendar = Calendar.getInstance()

        //화면 설정
        setMonthView()


        //이전달 버튼 이벤트
        binding.preBtn.setOnClickListener {
            //현재 월 -1 변수에 담기
            CalendarUtil.selectedDate.add(Calendar.MONTH, -1)// 현재 달 -1
            setMonthView()
        }

        //다음달 버튼 이벤트
        binding.nextBtn.setOnClickListener {
            CalendarUtil.selectedDate.add(Calendar.MONTH, 1) //현재 달 +1
            setMonthView()
        }

        return binding.root
    }

    //날짜 화면에 보여주기
    private fun setMonthView() {
        //년월 텍스트뷰 셋팅
        binding.monthYearText.text = monthYearFromDate(CalendarUtil.selectedDate)

        //날짜 생성해서 리스트에 담기
        val dayList = dayInMonthArray()

        //어댑터 초기화


        var monthText = monthYearFromDate(CalendarUtil.selectedDate).split(" 월")
        Log.d("monthText", "monthText: $monthText")
        var monthData: String = ""

        if(monthText[0].toInt()<10){
            monthData = "0" + monthText[0]
        } else {monthData = monthText[0]}


        //보내보자 리퀘스트 받아보자 리스폰스
        val userDataCal = TitleRequestBody(
            couple_index = "1",
            selected_month = monthData
        )
        Log.d("유저데이터", "userDataCal: $userDataCal")

        var TitleResponseBody = listOf<TitleResponseBody>()

        RetrofitAPI.emgMedService3.addUserByEnqueue(userDataCal)
            .enqueue(object : retrofit2.Callback<ArrayList<TitleResponseBody>> {
                override fun onResponse(
                    call: Call<ArrayList<TitleResponseBody>>,
                    response: Response<ArrayList<TitleResponseBody>>
                ) {
                    Toast.makeText(context, "Call Success", Toast.LENGTH_SHORT)
                        .show()

                    if (response.isSuccessful) {
                        Log.d("리턴", "onResponse: ${response.body()}")

                        TitleResponseBody = response.body()?: listOf()

                        val adapter = CalendarAdapter(dayList, TitleResponseBody)
                        //adapter.setList(TitleResponseBody)
                        Log.d("리턴1", "onResponse: $TitleResponseBody")

                        //레이아웃 설정(열 7개)
                        var manager: RecyclerView.LayoutManager = GridLayoutManager(context, 7)

                        //레이아웃 적용
                        binding.recyclerView.layoutManager = manager

                        //어댑터 적용
                        binding.recyclerView.adapter = adapter
                    }
                }

                override fun onFailure(
                    call: Call<ArrayList<TitleResponseBody>>,
                    t: Throwable
                ) {

                }
            })

    }


    //날짜 타입 설정(00월 0000년)
    private fun monthYearFromDate(calendar: Calendar): String {

        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH) + 1

        return "$month 월 $year"
    }


    //날짜 생성
    private fun dayInMonthArray(): ArrayList<Date>{

        var dayList = ArrayList<Date>()

        var monthCalendar = CalendarUtil.selectedDate.clone() as Calendar

        //1일로 셋팅
        monthCalendar[Calendar.DAY_OF_MONTH] = 1

        //해당 달의 1일의 요일[1:일요일, 2: 월요일.... 7일: 토요일]
        val firstDayOfMonth = monthCalendar[Calendar.DAY_OF_WEEK]-1

        //요일 숫자만큼 이전 날짜로 설정
        //예: 6월1일이 수요일이면 3만큼 이전날짜 셋팅
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth)

        while(dayList.size < 42){

            dayList.add(monthCalendar.time)

            //1일씩 늘린다. 1일 -> 2일 -> 3일
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return dayList
    }

}
