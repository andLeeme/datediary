package com.project.datediary.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.R
import com.project.datediary.model.dataVO
import com.project.datediary.model.titleTest
import com.project.datediary.util.CalendarUtil
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Objects

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
        holder.dayText.setTypeface(null, Typeface.NORMAL)

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
            holder.dayText.setTextColor(Color.parseColor("#000000"))

            Log.d("넘어온날짜", "iYear: $iYear, iMonth: $iMonth, iDay: $iDay")
            Log.d(
                "비교",
                "selectDay: $selectDay, dayNo: $dayNo iMonth: $iMonth selectMonth $selectMonth"
            )
            Log.d(
                "현재날짜",
                "selectYear: $selectYear, selectMonth: $selectMonth, selectDay: $selectDay"
            )

            //현재 날짜 비교해서 같다면 배경색상 변경
            if (iYear == selectYear && iMonth2 == selectMonth && selectDay == dayNo) {
                holder.dayText.setTextColor(Color.rgb(154, 132, 188))
                holder.dayText.setTypeface(Typeface.DEFAULT, Typeface.BOLD)
            }
//            else if (selectDay != dayNo) {
//                holder.itemView.setBackgroundColor(Color.WHITE)
//            }
            //텍스트 색상 지정(토,일)
            if ((position + 1) % 7 == 0) { //토요일은 파랑
                holder.dayText.setTextColor(Color.BLUE)

            } else if (position == 0 || position % 7 == 0) { //일요일은 빨강
                holder.dayText.setTextColor(Color.RED)
            }
        } else { //다르다면 연한 색상
            holder.dayText.setTextColor(Color.parseColor("#B4B4B4"))

            //텍스트 색상 지정(토,일)
            if ((position + 1) % 7 == 0) { //토요일은 파랑
                holder.dayText.setTextColor(Color.parseColor("#B4FFFF"))

            } else if (position == 0 || position % 7 == 0) { //일요일은 빨강
                holder.dayText.setTextColor(Color.parseColor("#FFB4B4"))
            }
        }

        //날짜 클릭 이벤트
        holder.itemView.setOnClickListener {

            var yearMonDay = "$iYear 년 $iMonth 월 $iDay 일"

            Toast.makeText(holder.itemView.context, yearMonDay, Toast.LENGTH_SHORT).show()
        }


        //날짜별 일정 추가
        //가져온 날짜랑 비교하기, 맞으면 텍스트 가져온 텍스트로 바꾸기, visible 처리


        var title = arrayListOf<titleTest>(
            titleTest("2023", "05", "14", "2023", "05", "14", true, "단일일정1"),
            titleTest("2023", "05", "14", "2023", "05", "14", true, "단일일정2"),
            titleTest("2023", "05", "05", "2023", "05", "20", true, ""),
            titleTest("2023", "05", "02", "2023", "05", "02", true, "")
        )

        val startYear = title[0].start_year.toInt()
        val startMonth = title[0].start_month.toInt()
        val startDay = title[0].start_day.toInt()

        if(selectYear == startYear && selectMonth == startMonth && dayNo == startDay) {

            for (i in 0 until title.size) {
                holder.schedule1.text = title[0].title
                holder.schedule2.text = title[1].title
                holder.schedule3.text = title[2].title
                holder.schedule4.text = title[3].title
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
