package com.example.timetable

import java.util.Calendar

fun getDefaultSelectedOption(): String {
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currDay = getCurrentDay()
    return (if (currentHour >= 17 || currDay == "SATURDAY" || currDay == "SUNDAY") nextDay[currDay] else currDay).toString()
}

val nextDay = mapOf(
    "MONDAY" to "TUESDAY",
    "TUESDAY" to "WEDNESDAY",
    "WEDNESDAY" to "THURSDAY",
    "THURSDAY" to "FRIDAY",
    "FRIDAY" to "MONDAY",
    "SATURDAY" to "MONDAY",
    "SUNDAY" to "MONDAY"
)