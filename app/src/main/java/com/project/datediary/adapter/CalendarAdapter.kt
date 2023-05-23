package com.project.datediary.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.R
import com.project.datediary.model.titleTest
import com.project.datediary.util.CalendarUtil
import java.util.Calendar
import java.util.Date


class CalendarAdapter(private val dayList: ArrayList<Date>) :
    RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dayText: TextView = itemView.findViewById(R.id.dayText)
        val schedule1: TextView = itemView.findViewById(R.id.schedule1)
        val schedule2: TextView = itemView.findViewById(R.id.schedule2)
        val schedule3: TextView = itemView.findViewById(R.id.schedule3)
        val schedule4: TextView = itemView.findViewById(R.id.schedule4)

    }

    //화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_grid, parent, false)

        return ItemViewHolder(view)
    }

    //데이터 설정
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        //날짜 변수에 담기
        var monthDate = dayList[holder.adapterPosition]

        //초기화
        var dateCalendar = Calendar.getInstance()

        //날짜 캘린더에 담기
        dateCalendar.time = monthDate

        //캘린더값 날짜 변수에 담기
        var dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH)

        holder.dayText.text = dayNo.toString()


        //넘어온 날짜
        var iYear = dateCalendar.get(Calendar.YEAR) //년
        var iMonth = dateCalendar.get(Calendar.MONTH) + 1 //월
        var iMonth2 = Calendar.getInstance().get(Calendar.MONTH) + 1 //월
        var iDay = dateCalendar.get(Calendar.DAY_OF_MONTH)//일


        //현재 날짜 (현재 달력 다음달로 넘기면 달이 바뀜) Calendar.getInstance()
        var selectYear = CalendarUtil.selectedDate.get(Calendar.YEAR) //년
        var selectMonth = CalendarUtil.selectedDate.get(Calendar.MONTH) + 1 //월
        var selectDay = CalendarUtil.selectedDate.get(Calendar.DAY_OF_MONTH) //일


        //val selectMonth2 = CalendarUtil.selectedDate.get(Calendar.MONTH) +1
        //넘어온 날짜와 현재 날짜 비교
        if (iYear == selectYear && iMonth == selectMonth) { //같다면 진한 색상
            holder.dayText.setTextColor(Color.parseColor("#606060"))

            //현재 날짜 비교해서 같다면 배경색상 변경
            if (iYear == selectYear && iMonth2 == selectMonth && selectDay == dayNo) {
                holder.dayText.setTextColor(Color.WHITE)
                holder.dayText.setBackgroundResource(R.drawable.day_circle)
                //holder.dayText.setTypeface(Typeface.DEFAULT, Typeface.BOLD)
            }

            //텍스트 색상 지정(토,일)
            if ((position + 1) % 7 == 0) { //토요일은 파랑
                holder.dayText.setTextColor(Color.parseColor("#b6d3e6"))

            } else if (position == 0 || position % 7 == 0) { //일요일은 빨강
                holder.dayText.setTextColor(Color.parseColor("#FFa4a4"))
            }
        } else { //다르다면 연한 색상
            holder.dayText.setTextColor(Color.parseColor("#B4B4B4"))

            holder.schedule1.visibility = View.INVISIBLE
            holder.schedule2.visibility = View.INVISIBLE
            holder.schedule3.visibility = View.INVISIBLE
            holder.schedule4.visibility = View.INVISIBLE

            //텍스트 색상 지정(토,일)
            if ((position + 1) % 7 == 0) { //토요일은 파랑
                holder.dayText.setTextColor(Color.parseColor("#D3ECFB"))

            } else if (position == 0 || position % 7 == 0) { //일요일은 빨강
                holder.dayText.setTextColor(Color.parseColor("#FAd1d1"))
            }
        }

        //날짜 클릭 이벤트
        holder.itemView.setOnClickListener {

            var yearMonDay = "$iYear 년 $iMonth 월 $iDay 일"

            //클릭 풀리면 배경 돌리기
            holder.itemView.setBackgroundColor(Color.LTGRAY)

            Toast.makeText(holder.itemView.context, yearMonDay, Toast.LENGTH_SHORT).show()
        }


        //날짜별 일정 추가
        //가져온 날짜랑 비교하기, 맞으면 텍스트 가져온 텍스트로 바꾸기, visible 처리

        var title = arrayListOf<titleTest>(
            titleTest("2023", "05", "02", "2023", "05", "04", true, "111"),
            titleTest("2023", "05", "02", "2023", "05", "02", true, "222"),
            titleTest("2023", "05", "12", "2023", "05", "15", true, "333"),
            titleTest("2023", "05", "14", "2023", "05", "14", true, "444"),
            titleTest("2023", "05", "14", "2023", "05", "14", true, "555"),
            titleTest("2023", "05", "15", "2023", "05", "15", false, "666"),
            titleTest("2023", "05", "15", "2023", "05", "15", false, "104"),
            titleTest("2023", "05", "15", "2023", "05", "15", true, "777"),
            titleTest("2023", "05", "16", "2023", "05", "17", true, "888"),
            titleTest("2023", "05", "18", "2023", "05", "18", true, "999"),
            titleTest("2023", "05", "19", "2023", "05", "19", false, "000"),
            titleTest("2023", "05", "22", "2023", "05", "25", true, "101"),
            titleTest("2023", "05", "22", "2023", "05", "22", true, "102"),
            titleTest("2023", "05", "22", "2023", "05", "22", true, "103"),
            titleTest("2023", "05", "20", "2023", "05", "20", false, "105"),
            titleTest("2023", "05", "20", "2023", "05", "20", false, "106"),
            titleTest("2023", "05", "20", "2023", "05", "20", false, "107"),
            titleTest("2023", "05", "20", "2023", "05", "20", false, "108"),
            )


        for (i in 0 until title.size) {
            //타이틀 add 해주기
        }
        //없으면 빈 배열 add해주기
        if (title.size == 0) {
            title.add(titleTest("", "", "", "", "", "", true, ""))
            title.add(titleTest("", "", "", "", "", "", true, ""))
            title.add(titleTest("", "", "", "", "", "", true, ""))
            title.add(titleTest("", "", "", "", "", "", true, ""))
        } else if (title.size == 1) {
            title.add(titleTest("", "", "", "", "", "", true, ""))
            title.add(titleTest("", "", "", "", "", "", true, ""))
            title.add(titleTest("", "", "", "", "", "", true, ""))
        } else if (title.size == 2) {
            title.add(titleTest("", "", "", "", "", "", true, ""))
            title.add(titleTest("", "", "", "", "", "", true, ""))
        } else if (title.size == 3) {
            title.add(titleTest("", "", "", "", "", "", true, ""))
        }


        for (i in 0 until title.size) {
            if (title[i].start_year != "") {
                val startYear = title[i].start_year.toInt()
                val startMonth = title[i].start_month.toInt()
                val startDay = title[i].start_day.toInt()
                val endDay = title[i].end_day.toInt()

                Log.d("테스트", "startMonth1: $startMonth")


                //event가 들어갈 날짜 계산
                if (selectYear == startYear && selectMonth == startMonth && dayNo > startDay - 1 && dayNo < endDay + 1) {

                    //1~4번칸 순차적으로 지정
                    if (holder.schedule1.text == "") {

                        //하루 일정이 아니면(연일 일정이면)
                        if (startDay - endDay != 0) {

                            //시작일 종료일 배경 설정
                            if (dayNo == startDay) {
                                holder.schedule1.setBackgroundResource(R.drawable.schedule_background1_start)
                            } else if (dayNo == endDay) {
                                holder.schedule1.setBackgroundResource(R.drawable.schedule_background1_end)
                            } else {
                                holder.schedule1.setBackgroundResource(R.drawable.schedule_background1_middle)
                            }

                            //텍스트 중앙에 위치시키기
                            if (dayNo == (startDay + endDay) / 2) {
                                holder.schedule1.text = title[i].title
                                holder.schedule1.setGravity(Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL)

                                //짝수연일 일정(4일짜리)는 중앙에서(2번째 날)에서 오른쪽 정렬
                                if ((endDay - startDay) % 2 == 1) {
                                    holder.schedule1.setGravity(Gravity.RIGHT or Gravity.CENTER_HORIZONTAL)
                                }

                                //짝수연일 일정(2일짜리)는 그냥 왼쪽 정렬
                                if ((endDay - startDay) == 1) {
                                    holder.schedule1.setGravity(Gravity.LEFT or Gravity.CENTER_HORIZONTAL)
                                }

                            }
                            //텍스트 중앙에 위치시키면서 다른 칸들은 공백처리, ""은 안됨 " "이어야 됨
                            else {
                                holder.schedule1.text = " "
                            }
                        }
                            //하루일정이고 allDayCheck가 false일 때
                            else if (startDay - endDay == 0 && !title[i].allDayCheck) {
                            holder.schedule1.setBackgroundResource(R.drawable.schedule_background1_simple)
                            holder.schedule1.setTextColor(Color.BLACK)
                            holder.schedule1.setGravity(Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL)
                            holder.schedule1.text = title[i].title
                        }
                            //기본형
                            else holder.schedule1.text = title[i].title

                    ////////////////////////////2번칸/////////////////////////////////////
                    } else if (holder.schedule2.text == "") {
                        //2번칸 기본 배경
                        //holder.schedule1.setBackgroundResource(R.drawable.schedule_background1)

                        //하루 일정이 아니면(연일 일정이면)
                        if (startDay - endDay != 0) {

                            //시작일 종료일 배경 설정
                            if (dayNo == startDay) {
                                holder.schedule2.setBackgroundResource(R.drawable.schedule_background2_start)
                            } else if (dayNo == endDay) {
                                holder.schedule2.setBackgroundResource(R.drawable.schedule_background2_end)
                            } else {
                                holder.schedule2.setBackgroundResource(R.drawable.schedule_background2_middle)
                            }

                            //텍스트 중앙에 위치시키기
                            if (dayNo == (startDay + endDay) / 2) {
                                holder.schedule2.text = title[i].title
                                holder.schedule2.setGravity(Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL)

                                //짝수연일 일정(4일짜리)는 중앙에서(2번째 날)에서 오른쪽 정렬
                                if ((endDay - startDay) % 2 == 1) {
                                    holder.schedule2.setGravity(Gravity.RIGHT or Gravity.CENTER_HORIZONTAL)
                                }

                                //짝수연일 일정(2일짜리)는 그냥 왼쪽 정렬
                                if ((endDay - startDay) == 1) {
                                    holder.schedule2.setGravity(Gravity.LEFT or Gravity.CENTER_HORIZONTAL)
                                }

                            }
                            //텍스트 중앙에 위치시키면서 다른 칸들은 공백처리, ""은 안됨 " "이어야 됨
                            else {
                                holder.schedule2.text = " "
                            }
                        }
                        //하루일정이고 allDayCheck가 false일 때
                        else if (startDay - endDay == 0 && !title[i].allDayCheck) {
                            holder.schedule2.setBackgroundResource(R.drawable.schedule_background2_simple)
                            holder.schedule2.setTextColor(Color.BLACK)
                            holder.schedule2.setGravity(Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL)
                            holder.schedule2.text = title[i].title
                        }
                        //기본형
                        else holder.schedule2.text = title[i].title

                    ////////////////////////////3번칸/////////////////////////////////////
                    } else if (holder.schedule3.text == "") {
                        //3번칸 기본 배경
                        //holder.schedule1.setBackgroundResource(R.drawable.schedule_background1)

                        //하루 일정이 아니면(연일 일정이면)
                        if (startDay - endDay != 0) {

                            //시작일 종료일 배경 설정
                            if (dayNo == startDay) {
                                holder.schedule3.setBackgroundResource(R.drawable.schedule_background3_start)
                            } else if (dayNo == endDay) {
                                holder.schedule3.setBackgroundResource(R.drawable.schedule_background3_end)
                            } else {
                                holder.schedule3.setBackgroundResource(R.drawable.schedule_background3_middle)
                            }

                            //텍스트 중앙에 위치시키기
                            if (dayNo == (startDay + endDay) / 2) {
                                holder.schedule3.text = title[i].title
                                holder.schedule3.setGravity(Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL)

                                //짝수연일 일정(4일짜리)는 중앙에서(2번째 날)에서 오른쪽 정렬
                                if ((endDay - startDay) % 2 == 1) {
                                    holder.schedule3.setGravity(Gravity.RIGHT or Gravity.CENTER_HORIZONTAL)
                                }

                                //짝수연일 일정(2일짜리)는 그냥 왼쪽 정렬
                                if ((endDay - startDay) == 1) {
                                    holder.schedule3.setGravity(Gravity.LEFT or Gravity.CENTER_HORIZONTAL)
                                }

                            }
                            //텍스트 중앙에 위치시키면서 다른 칸들은 공백처리, ""은 안됨 " "이어야 됨
                            else {
                                holder.schedule3.text = " "
                            }
                        }
                        //하루일정이고 allDayCheck가 false일 때
                        else if (startDay - endDay == 0 && !title[i].allDayCheck) {
                            holder.schedule3.setBackgroundResource(R.drawable.schedule_background3_simple)
                            holder.schedule3.setTextColor(Color.BLACK)
                            holder.schedule3.setGravity(Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL)
                            holder.schedule3.text = title[i].title
                        }
                        //기본형
                        else holder.schedule3.text = title[i].title

                        ////////////////////////////4번칸/////////////////////////////////////
                    } else if (holder.schedule4.text == "") {
                        //4번칸 기본 배경
                        //holder.schedule1.setBackgroundResource(R.drawable.schedule_background1)

                        //하루 일정이 아니면(연일 일정이면)
                        if (startDay - endDay != 0) {

                            //시작일 종료일 배경 설정
                            if (dayNo == startDay) {
                                holder.schedule4.setBackgroundResource(R.drawable.schedule_background4_start)
                            } else if (dayNo == endDay) {
                                holder.schedule4.setBackgroundResource(R.drawable.schedule_background4_end)
                            } else {
                                holder.schedule4.setBackgroundResource(R.drawable.schedule_background4_middle)
                            }

                            //텍스트 중앙에 위치시키기
                            if (dayNo == (startDay + endDay) / 2) {
                                holder.schedule4.text = title[i].title
                                holder.schedule4.setGravity(Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL)

                                //짝수연일 일정(4일짜리)는 중앙에서(2번째 날)에서 오른쪽 정렬
                                if ((endDay - startDay) % 2 == 1) {
                                    holder.schedule4.setGravity(Gravity.RIGHT or Gravity.CENTER_HORIZONTAL)
                                }

                                //짝수연일 일정(2일짜리)는 그냥 왼쪽 정렬
                                if ((endDay - startDay) == 1) {
                                    holder.schedule4.setGravity(Gravity.LEFT or Gravity.CENTER_HORIZONTAL)
                                }

                            }
                            //텍스트 중앙에 위치시키면서 다른 칸들은 공백처리, ""은 안됨 " "이어야 됨
                            else {
                                holder.schedule4.text = " "
                            }
                        }
                        //하루일정이고 allDayCheck가 false일 때
                        else if (startDay - endDay == 0 && !title[i].allDayCheck) {
                            holder.schedule4.setBackgroundResource(R.drawable.schedule_background4_simple)
                            holder.schedule4.setTextColor(Color.BLACK)
                            holder.schedule4.setGravity(Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL)
                            holder.schedule4.text = title[i].title
                        }
                        //기본형
                        else holder.schedule4.text = title[i].title
                    }
                }
            }
        }

//        if(iMonth2 != selectMonth) {
//            holder.schedule1.visibility = View.GONE
//            holder.schedule2.visibility = View.GONE
//            holder.schedule3.visibility = View.GONE
//            holder.schedule4.visibility = View.GONE
//        }


        //일정 visible 처리
        if (holder.schedule1.text == "") {
            holder.schedule1.visibility = View.GONE
        }

        if (holder.schedule2.text == "") {
            holder.schedule2.visibility = View.GONE
        }
        if (holder.schedule3.text == "") {
            holder.schedule3.visibility = View.GONE
        }
        if (holder.schedule4.text == "") {
            holder.schedule4.visibility = View.GONE
        }

    }


    override fun getItemCount(): Int {
        return dayList.size
    }
}

