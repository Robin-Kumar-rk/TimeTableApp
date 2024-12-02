package com.example.timetable.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Lecture::class], version = 1, exportSchema = false)
abstract class LectureDatabase : RoomDatabase() {
    abstract fun lectureDao(): LectureDao
}
