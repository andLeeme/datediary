package com.project.datediary.util

import android.util.Log
import android.widget.Toast
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlin.coroutines.coroutineContext

interface CalendarUtil {
    companion object {
        var selectedDate: Calendar = Calendar.getInstance()

        val current = LocalDateTime.now()
        val formatterYear = DateTimeFormatter.ofPattern("yyyy")
        val formatterMonth = DateTimeFormatter.ofPattern("M")
        val formatterDay = DateTimeFormatter.ofPattern("d")


        var sYear: String = current.format(formatterYear).toString()
        var sMonth: String = current.format(formatterMonth).toString()
        var sDay: String = current.format(formatterDay).toString()

        fun logDate() {
            Log.d("DateFromAdapter", "toastDate: $sYear. $sMonth. $sDay")
        }

    }
}