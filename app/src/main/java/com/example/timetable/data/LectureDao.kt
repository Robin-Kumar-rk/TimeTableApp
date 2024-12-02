package com.example.timetable.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LectureDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertLecture(lecture: Lecture)

    @Update
    abstract suspend fun updateLecture(lecture: Lecture)

    @Delete
    abstract suspend fun deleteLecture(lecture: Lecture)

    @Query("SELECT * FROM lecture_table WHERE day = :day")
    abstract fun getLecturesForDay(day: String): Flow<List<Lecture>>

    @Query("SELECT * FROM lecture_table WHERE id = :id")
    abstract fun getLectureById(id: Long): Flow<Lecture>

    @Query("SELECT COUNT(*) FROM lecture_table")
    abstract suspend fun getLectureCount(): Int //

}
