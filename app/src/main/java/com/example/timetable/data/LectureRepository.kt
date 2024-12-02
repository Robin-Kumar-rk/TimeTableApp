package com.example.timetable.data

import kotlinx.coroutines.flow.Flow

class LectureRepository(private val lectureDao: LectureDao) {

    fun getLecturesForDay(day: String): Flow<List<Lecture>> {
        return lectureDao.getLecturesForDay(day)
    }

    suspend fun insertLecture(lecture: Lecture) {
        lectureDao.insertLecture(lecture)
    }

    suspend fun updateLecture(lecture: Lecture) {
        lectureDao.updateLecture(lecture)
    }

    suspend fun deleteLecture(lecture: Lecture) {
        lectureDao.deleteLecture(lecture)
    }

    fun getLectureById(id: Long): Flow<Lecture> {
        return lectureDao.getLectureById(id)
    }
}
