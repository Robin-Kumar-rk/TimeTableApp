package com.example.timetable

import android.content.Context
import androidx.room.Room

import com.example.timetable.data.LectureDatabase
import com.example.timetable.data.LectureRepository


object Graph {
    lateinit var database: LectureDatabase

    val lectureRepository by lazy {
        LectureRepository(
            lectureDao = database.lectureDao()
        )
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(
            context,
            LectureDatabase::class.java,
            "lecture_database"
        ).build()
    }

}
