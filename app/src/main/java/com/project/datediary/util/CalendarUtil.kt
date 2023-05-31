package com.project.datediary.util

import android.util.Log
import android.widget.Toast
import java.time.LocalDate
import java.util.Calendar
import kotlin.coroutines.coroutineContext

interface CalendarUtil {
    companion object {
        var selectedDate: Calendar = Calendar.getInstance()

        var sYear: String = ""
        var sMonth: String = ""
        var sDay: String = ""

        fun logDate() {
            Log.d("DateFromAdapter", "toastDate: $sYear. $sMonth. $sDay")
        }
    }
}