package com.project.datediary.util

import java.time.LocalDate
import java.util.Calendar

interface CalendarUtil {
    companion object {
        var selectedDate: Calendar = Calendar.getInstance()
    }
}