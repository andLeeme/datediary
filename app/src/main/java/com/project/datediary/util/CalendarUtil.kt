package com.project.datediary.util

import java.time.LocalDate

interface CalendarUtil {
    companion object {
        var selectedDate: LocalDate = LocalDate.now()
    }
}