package com.project.datediary.fragment

import RetrofitAPI
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.project.datediary.activity.AddScheduleActivity
import com.project.datediary.activity.EditScheduleActivity
import com.project.datediary.activity.MainActivity
import com.project.datediary.adapter.CalendarAdapter
import com.project.datediary.adapter.DayScheduleAdapter
import com.project.datediary.databinding.FragmentCalendarBinding
import com.project.datediary.model.TitleRequestBody
import com.project.datediary.model.TitleResponseBody
import com.project.datediary.util.CalendarUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.util.Calendar
import java.util.Date


class FragmentCalendar : Fragment(), MainActivity.onBackPressedListener {

    lateinit var binding: FragmentCalendarBinding
    lateinit var DayScheduleAdapter: DayScheduleAdapter
    var month_view = Int

    //lateinit var calendar: Calendar

    //년월 변수
    //lateinit var selectedDate: LocalDate


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        //화면 설정
        setMonthView()

        binding.addBtn.setOnClickListener {
            val intent = Intent(context, AddScheduleActivity::class.java)
            startActivity(intent)

            CoroutineScope(Dispatchers.Main).launch {
                delay(200).run {
                    bottomDown()
                }
            }

        }

        var bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        binding.image1.setOnClickListener {

            //BottomSheetBehavior.from(bottomSheetBehavior의 자식 요소 넣어주기)
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            }
            Log.d(
                "DateFromUtil",
                "onCreate: ${CalendarUtil.sMonth}.${CalendarUtil.sDay}.${CalendarUtil.sYear}"
            )
        }


        //이전달 버튼 이벤트
        binding.preBtn.setOnClickListener {
            //현재 월 -1 변수에 담기
            CalendarUtil.selectedDate.add(Calendar.MONTH, -1)// 현재 달 -1
            setMonthView()
            bottomDown()
        }

        //다음달 버튼 이벤트
        binding.nextBtn.setOnClickListener {
            CalendarUtil.selectedDate.add(Calendar.MONTH, 1) //현재 달 +1
            setMonthView()
            bottomDown()
        }


        //상단 날짜 누르면 bottomsheet 내려감
        binding.monthYearText.setOnClickListener { bottomDown() }



        return binding.root
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            delay(200).run {
                setMonthView()
            }
        }
    }

    //날짜 화면에 보여주기
    private fun setMonthView() {


        //년월 텍스트뷰 셋팅
        binding.monthYearText.text = monthYearFromDate(CalendarUtil.selectedDate)
        binding.selectedDay.text = CalendarUtil.sDay
        binding.selectedDW.text = CalendarUtil.sDOW


        //날짜 생성해서 리스트에 담기
        val dayList = dayInMonthArray()

        //어댑터 초기화


        var monthText = monthYearFromDate(CalendarUtil.selectedDate).split(" 월")
        Log.d("monthText", "monthText: $monthText")
        var monthData: String = monthText[0]

//        if(monthText[0].toInt()<10){
//            monthData = "0" + monthText[0]
//        } else {monthData = monthText[0]}


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
//                    Toast.makeText(context, "Call Success", Toast.LENGTH_SHORT)
//                        .show()

                    if (response.isSuccessful) {
                        Log.d("리턴", "onResponse: ${response.body()}")

                        TitleResponseBody = response.body() ?: listOf()

                        val adapter = CalendarAdapter(dayList, TitleResponseBody)
                        Log.d("리턴1", "onResponse: $TitleResponseBody")

                        //레이아웃 설정(열 7개)
                        var manager: RecyclerView.LayoutManager = GridLayoutManager(context, 7)

                        //레이아웃 적용
                        binding.recyclerView.layoutManager = manager

                        //어댑터 적용
                        binding.recyclerView.adapter = adapter


                        //////////// ///////오늘 정보 일정 바텀시트에 그려주기////////////////////////
                        //오늘 정보 가공
                        var scheduleList = ArrayList<TitleResponseBody>()
                        Log.d("scheduleList1", "bind: ${TitleResponseBody}")

                        for (i in TitleResponseBody.indices) {
                            if (TitleResponseBody[i].startDay == CalendarUtil.sDay) {
                                scheduleList.add(TitleResponseBody[i])
                            }
                        }
                        Log.d("scheduleList2", "bind: $scheduleList")


                        //어댑터에 넣어주기
                        val adapter2 = DayScheduleAdapter(scheduleList)


                        //레이아웃 설정
                        var manager2: RecyclerView.LayoutManager = LinearLayoutManager(context)

                        //레이아웃 적용
                        binding.recycler10.layoutManager = manager2

                        //어댑터 적용
                        binding.recycler10.adapter = adapter2


                        //아이템 클릭하면 EditSchedule Activity 소환!
                        adapter2.dayScheduleSetItemClickListener(object :
                            DayScheduleAdapter.DayScheduleOnItemClickListener {
                            override fun dayScheduleOnClick(v: View, position: Int) {
                                Log.d(
                                    "testIndex",
                                    "dayScheduleOnClick: ${scheduleList[position].scheduleIndex}"
                                )
                                val intent =
                                    Intent(context, EditScheduleActivity::class.java)
                                Log.d(
                                    "testIndex1",
                                    "dayScheduleOnClick: ${scheduleList[position].scheduleIndex}"
                                )
                                intent.putExtra(
                                    "scheduleIndex",
                                    scheduleList[position].scheduleIndex
                                )
                                intent.putExtra("startYear", scheduleList[position].startYear)
                                intent.putExtra("startMonth", scheduleList[position].startMonth)
                                intent.putExtra("startDay", scheduleList[position].startDay)
                                intent.putExtra("startTime", scheduleList[position].startTime)
                                intent.putExtra("endYear", scheduleList[position].endYear)
                                intent.putExtra("endMonth", scheduleList[position].endMonth)
                                intent.putExtra("endDay", scheduleList[position].endDay)
                                intent.putExtra("endTime", scheduleList[position].endTime)
                                intent.putExtra("title", scheduleList[position].title)
                                intent.putExtra("contents", scheduleList[position].contents)
                                intent.putExtra("allDayCheck", scheduleList[position].allDayCheck)
                                intent.putExtra("placeCode", scheduleList[position].placeCode)
                                intent.putExtra("missionCode", scheduleList[position].missionCode)
                                Log.d(
                                    "testIndex2",
                                    "dayScheduleOnClick: ${scheduleList[position].scheduleIndex}"
                                )
                                startActivity(intent)
                            }
                        })


                        adapter.setItemClickListener(object : CalendarAdapter.OnItemClickListener {
                            override fun onClick(v: View, position: Int) {
                                binding.image1.performClick()

                                //선택한 날의 날짜와 요일을 바텀시트에 그려줌
                                binding.selectedDay.text = CalendarUtil.sDay
                                var DWText = binding.selectedDW
                                if (position == 0) {
                                    DWText.text = "일요일"
                                } else {
                                    when (position % 7) {
                                        0 -> DWText.text = "일요일"
                                        1 -> DWText.text = "월요일"
                                        2 -> DWText.text = "화요일"
                                        3 -> DWText.text = "수요일"
                                        4 -> DWText.text = "목요일"
                                        5 -> DWText.text = "금요일"
                                        6 -> DWText.text = "토요일"
                                    }
                                }

                                //////////// ///////선택한 날의 정보 일정 바텀시트에 그려주기////////////////////////

                                //선택한 날의 정보 가공
                                var scheduleList = ArrayList<TitleResponseBody>()
                                Log.d("scheduleList1", "bind: ${TitleResponseBody}")

                                for (i in TitleResponseBody.indices) {
                                    if (TitleResponseBody[i].startDay == CalendarUtil.sDay) {
                                        scheduleList.add(TitleResponseBody[i])
                                    }
                                }
                                Log.d("scheduleList2", "bind: $scheduleList")


                                //어댑터에 넣어주기
                                val adapter2 = DayScheduleAdapter(scheduleList)

                                //레이아웃 설정
                                var manager2: RecyclerView.LayoutManager =
                                    LinearLayoutManager(context)

                                //레이아웃 적용
                                binding.recycler10.layoutManager = manager2

                                //어댑터 적용
                                binding.recycler10.adapter = adapter2

                                //아이템 클릭하면 EditSchedule Activity 소환!
                                adapter2.dayScheduleSetItemClickListener(object :
                                    DayScheduleAdapter.DayScheduleOnItemClickListener {
                                    override fun dayScheduleOnClick(v: View, position: Int) {
                                        val intent =
                                            Intent(context, EditScheduleActivity::class.java)
                                        Log.d("testIndex1", "dayScheduleOnClick: ${scheduleList[position].scheduleIndex}")
                                        intent.putExtra("scheduleIndex", scheduleList[position].scheduleIndex)
                                        intent.putExtra("startYear", scheduleList[position].startYear)
                                        intent.putExtra("startMonth", scheduleList[position].startMonth)
                                        intent.putExtra("startDay", scheduleList[position].startDay)
                                        intent.putExtra("startTime", scheduleList[position].startTime)
                                        intent.putExtra("endYear", scheduleList[position].endYear)
                                        intent.putExtra("endMonth", scheduleList[position].endMonth)
                                        intent.putExtra("endDay", scheduleList[position].endDay)
                                        intent.putExtra("endTime", scheduleList[position].endTime)
                                        intent.putExtra("title", scheduleList[position].title)
                                        intent.putExtra("contents", scheduleList[position].contents)
                                        intent.putExtra("allDayCheck", scheduleList[position].allDayCheck)
                                        intent.putExtra("placeCode", scheduleList[position].placeCode)
                                        intent.putExtra("missionCode", scheduleList[position].missionCode)
                                        Log.d("testIndex2", "dayScheduleOnClick: ${scheduleList[position].scheduleIndex}")
                                        startActivity(intent)
                                    }
                                })

                            }
                        })
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
    private fun dayInMonthArray(): ArrayList<Date> {

        var dayList = ArrayList<Date>()

        var monthCalendar = CalendarUtil.selectedDate.clone() as Calendar

        //1일로 셋팅
        monthCalendar[Calendar.DAY_OF_MONTH] = 1

        //해당 달의 1일의 요일[1:일요일, 2: 월요일.... 7일: 토요일]
        val firstDayOfMonth = monthCalendar[Calendar.DAY_OF_WEEK] - 1

        //요일 숫자만큼 이전 날짜로 설정
        //예: 6월1일이 수요일이면 3만큼 이전날짜 셋팅
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth)

        while (dayList.size < 42) {

            dayList.add(monthCalendar.time)

            //1일씩 늘린다. 1일 -> 2일 -> 3일
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return dayList
    }

    fun bottomGetState(): Int {
        var bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        var bottomState = bottomSheetBehavior.getState()
        Log.d("BTStateGet", "onResponse: $bottomState")

        return bottomState
    }

    fun bottomDown() {
        var bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.setState(STATE_COLLAPSED)
        var bottomState = bottomSheetBehavior.getState()
        Log.d("BTStateSet", "onResponse: $bottomState")
    }

    override fun onBackPressed() {

        var bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        } else {
            (activity as MainActivity).callHome()
        }
    }


}



