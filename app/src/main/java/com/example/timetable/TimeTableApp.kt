package com.example.timetable

import android.app.Application
import com.example.timetable.data.Lecture
import com.example.timetable.data.LectureDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimeTableApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)

        CoroutineScope(Dispatchers.IO).launch {
            val lectureDao = Graph.database.lectureDao()
            val lecturesCount = lectureDao.getLectureCount() // Step 1: Check if lectures exist
            if (lecturesCount == 0) {
                populateInitialData(lectureDao) // Step 2: Populate only if the database is empty
            }
        }
    }

    private suspend fun populateInitialData(lectureDao: LectureDao) {
            val timetable = listOf(
                Lecture(day = "MONDAY", period = "1", lectureName = "B1 COA LAB\nB2 WDW LAB", time = "8:50-10:30", color = "#FF7A9EF7", order = "1"),
                Lecture(day = "MONDAY", period = "3", lectureName = "Math", time = "10:30-11:20", color = "#FFF8F871", order = "2"),
                Lecture(day = "MONDAY", period = "4", lectureName = "DS", time = "11:20-12:10", color = "#FF76FCFC", order = "3"),
                Lecture(day = "MONDAY", period = "5", lectureName = "COA", time = "12:10-1:00", color = "#FF98FA63", order = "4"),
                Lecture(day = "MONDAY", period = "", lectureName = "Lunch", time = "1:00-1:50", color = "#FFFFFF", order = "5"),
                Lecture(day = "MONDAY", period = "6", lectureName = "UHV", time = "1:50-2:40", color = "#FFF86F6F", order = "6"),
                Lecture(day = "MONDAY", period = "7", lectureName = "DS(Weak)", time = "2:40-3:30", color = "#FFFFFFFF", order = "7"),
                Lecture(day = "MONDAY", period = "8", lectureName = "Library", time = "3:30-4:20", color = "#FFFFFFFF", order = "8"),

                Lecture(day = "TUESDAY", period = "1", lectureName = "COA", time = "8:50-9:40", color = "#FF98FA63", order = "1"),
                Lecture(day = "TUESDAY", period = "2", lectureName = "Math", time = "9:40-10:30", color = "#FFF8F871", order = "2"),
                Lecture(day = "TUESDAY", period = "3", lectureName = "Python", time = "10:30-11:20", color = "#FFF56DD7", order = "3"),
                Lecture(day = "TUESDAY", period = "4", lectureName = "DS", time = "11:20-12:10", color = "#FF76FCFC", order = "4"),
                Lecture(day = "TUESDAY", period = "5", lectureName = "DS & TL", time = "12:10-1:00", color = "#FFB69FF7", order = "5"),
                Lecture(day = "TUESDAY", period = "", lectureName = "Lunch", time = "1:00-1:50", color = "#FFFFFF", order = "6"),
                Lecture(day = "TUESDAY", period = "6", lectureName = "Training(C)", time = "1:50-3:30", color = "#FFFFFFFF", order = "7"),
                Lecture(day = "TUESDAY", period = "7", lectureName = "Math(Weak)", time = "3:30-4:20", color = "#FFFFFFFF", order = "8"),

                Lecture(day = "WEDNESDAY", period = "1", lectureName = "DS", time = "8:50-9:40", color = "#FF76FCFC", order = "1"),
                Lecture(day = "WEDNESDAY", period = "2", lectureName = "DS & TL", time = "9:40-10:30", color = "#FFB69FF7", order = "2"),
                Lecture(day = "WEDNESDAY", period = "3", lectureName = "B1 WDW LAB 5\nB2 DS LAB 9", time = "10:30-12:10", color = "#FF7A9EF7", order = "3"),
                Lecture(day = "WEDNESDAY", period = "5", lectureName = "UHV", time = "12:10-1:00", color = "#FFF86F6F", order = "4"),
                Lecture(day = "WEDNESDAY", period = "", lectureName = "Lunch", time = "1:00-1:50", color = "#FFFFFFFF", order = "5"),
                Lecture(day = "WEDNESDAY", period = "6", lectureName = "Math", time = "1:50-2:40", color = "#FFF8F871", order = "6"),
                Lecture(day = "WEDNESDAY", period = "7", lectureName = "DS & TL", time = "2:40-3:30", color = "#FFB69FF7", order = "7"),
                Lecture(day = "WEDNESDAY", period = "8", lectureName = "COA(Weak)", time = "3:30-4:20", color = "#FFFFFFFF", order = "8"),

                Lecture(day = "THURSDAY", period = "1", lectureName = "CB1 DS LAB 9\nB2 COA LAB", time = "8:50-10:30", color = "#FF7A9EF7", order = "1"),
                Lecture(day = "THURSDAY", period = "3", lectureName = "DS & TL", time = "10:30-11:20", color = "#FFB69FF7", order = "2"),
                Lecture(day = "THURSDAY", period = "4", lectureName = "Math", time = "11:20-12:10", color = "#FFF8F871", order = "3"),
                Lecture(day = "THURSDAY", period = "5", lectureName = "DS", time = "12:10-1:00", color = "#FF76FCFC", order = "4"),
                Lecture(day = "THURSDAY", period = "", lectureName = "Lunch", time = "1:00-1:50", color = "#FFFFFFFF", order = "5"),
                Lecture(day = "THURSDAY", period = "6", lectureName = "Python", time = "1:50-2:40", color = "#FFF56DD7", order = "6"),
                Lecture(day = "THURSDAY", period = "7", lectureName = "COA", time = "2:40-3:30", color = "#FF98FA63", order = "7"),
                Lecture(day = "THURSDAY", period = "8", lectureName = "DS & TL(Weak)", time = "3:30-4:20", color = "#FFFFFFFF", order = "8"),


                Lecture(day = "FRIDAY", period = "1", lectureName = "Python", time = "8:50-9:40", color = "#FFF56DD7", order = "1"),
                Lecture(day = "FRIDAY", period = "2", lectureName = "COA", time = "9:40-10:30", color = "#FF98FA63", order = "2"),
                Lecture(day = "FRIDAY", period = "3", lectureName = "MATH", time = "10:30-11:20", color = "#FFF8F871", order = "3"),
                Lecture(day = "FRIDAY", period = "4", lectureName = "UHV", time = "11:20-12:10", color = "#FFF86F6F", order = "4"),
                Lecture(day = "FRIDAY", period = "5", lectureName = "DS & TL", time = "12:10-1:00", color = "#FFB69FF7", order = "5"),
                Lecture(day = "FRIDAY", period = "", lectureName = "Lunch", time = "1:00-1:50", color = "#FFFFFF", order = "6"),
                Lecture(day = "FRIDAY", period = "6", lectureName = "COA", time = "1:50-2:40", color = "#FF98FA63", order = "7"),
                Lecture(day = "FRIDAY", period = "7", lectureName = "DS", time = "2:40-3:30", color = "#FF76FCFC", order = "8"),
                Lecture(day = "FRIDAY", period = "8", lectureName = "Python(Weak)", time = "3:30-4:20", color = "#FFFFFFFF", order = "9"),


                )

                timetable.forEach { lecture ->
                    lectureDao.insertLecture(lecture)
                }


    }
}