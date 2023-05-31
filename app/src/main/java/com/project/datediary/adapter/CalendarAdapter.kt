package com.project.datediary.adapter

import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.R
import com.project.datediary.model.TitleResponseBody
import com.project.datediary.util.CalendarUtil
import java.util.Calendar
import java.util.Date


class CalendarAdapter(private val dayList: ArrayList<Date>, private val TmpData: List<TitleResponseBody>) :
    RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

    var titleResponse = TmpData

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

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

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener

    //데이터 설정
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {


        Log.d("titleResponse", "onBindViewHolder: $titleResponse")
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
            CalendarUtil.sYear = iYear.toString()
            CalendarUtil.sMonth = iMonth.toString()
            CalendarUtil.sDay = iDay.toString()
            CalendarUtil.logDate()

            itemClickListener.onClick(it, position)
        }




        //날짜 클릭하면 오늘의 일정 나오게 함
//        holder.itemView.setOnClickListener {
//
//        }



        //날짜별 일정 추가
        //가져온 날짜랑 비교하기, 맞으면 텍스트 가져온 텍스트로 바꾸기, visible 처리

        var title = ArrayList<TitleResponseBody>()

        //titleResponse은 List<TitleResponseBody>형태
        for(i in titleResponse.indices) {
            title.add(titleResponse[i])
        }

        Log.d("title2", "onBindViewHolder: $title")


        for (i in 0 until title.size) {
            //타이틀 add 해주기
        }

        //없으면 빈 배열 add해주기
        if(title.size<4) {
            for( i in 1..(4-title.size)) {
                title.add(TitleResponseBody("", "", "", "", "", "", "true", ""))
            }
        }


        //title의 크기만큼 달력에 그려주기
        for (i in 0 until title.size) {
            //if (title[i].startYear != ""&& title[i].startMonth != "" && title[i].startDay != "" && title[i].endDay != "") {
            if (title[i].startYear != "") {
                val startYear = title[i].startYear?.toInt()
                val startMonth = title[i].startMonth?.toInt()
                val startDay = title[i].startDay?.toInt()
                val endDay = title[i].endDay?.toInt()

                Log.d("테스트", "startMonth1: $startMonth")


                //event가 들어갈 날짜 계산
                if (selectYear == startYear && selectMonth == startMonth && dayNo > startDay!! - 1 && dayNo < endDay!! + 1) {

                    //1~4번칸 순차적으로 지정
                    if (holder.schedule1.text == "") {


                        //하루 일정이 아니면(연일 일정이면)
                        if (startDay !=  endDay) {

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
                            else if (startDay  == endDay && title[i].allDayCheck=="false") {
                            holder.schedule1.setBackgroundResource(R.drawable.schedule_background1_simple)
                            holder.schedule1.setTextColor(Color.BLACK)
                            holder.schedule1.setGravity(Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL)
                            holder.schedule1.text = title[i].title
                        }
                            //기본형
                            else if(startDay  == endDay && title[i].allDayCheck=="true") {
                                holder.schedule1.text = title[i].title
                                holder.schedule1.setBackgroundResource(R.drawable.schedule_background1)
                        }


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
                        else if (startDay - endDay == 0 && title[i].allDayCheck=="false") {
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
                        else if (startDay - endDay == 0 && title[i].allDayCheck=="false") {
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
                        else if (startDay - endDay == 0 && title[i].allDayCheck=="false") {
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

