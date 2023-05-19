package com.project.datediary.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.R
import com.project.datediary.util.CalendarUtil
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

class CalendarAdapter(private val dayList: ArrayList<Date>):
    RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {


    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val dayText: TextView = itemView.findViewById(R.id.dayText)
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
        holder.dayText.setTextColor(Color.GRAY)  //대충 회색

        //현재 날짜 배경색상 변경
        if(CalendarUtil.selectedDate.dayOfMonth == dayNo){

            holder.dayText.setTextColor(Color.rgb(241, 158, 194)) //지현이가 정해준 예쁜 분홍색
            holder.dayText.setTypeface(null, Typeface.BOLD)
        }

        //텍스트 색상 지정(토,일)
        if ((position + 1) % 7 == 0) { //토요일은 파랑
            holder.dayText.setTextColor(Color.rgb(130  , 130, 200)) //대충 빨간색

        } else if (position == 0 || position % 7 == 0) { //일요일은 빨강
            holder.dayText.setTextColor(Color.rgb(200, 92, 115))  //대충 파란색
        }

        //날짜 클릭 이벤트
        holder.itemView.setOnClickListener {
            //인터페이스를 통해 날짜를 넘겨준다.
            var iYear   = dateCalendar.get(Calendar.YEAR)
            var iMonth  = dateCalendar.get(Calendar.MONTH) + 1
            var iDay    = dateCalendar.get(Calendar.DAY_OF_MONTH)

            var yearMonDay = "$iYear 년 $iMonth 월 $iDay 일"

            Toast.makeText(holder.itemView.context, yearMonDay, Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return dayList.size
    }
}