package com.example.timetable

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object AddLectureScreen: Screen("add_lecture_screen")

}