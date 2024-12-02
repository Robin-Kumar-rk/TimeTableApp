package com.example.timetable

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetable.data.LectureRepository
import com.example.timetable.data.Lecture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


class LectureViewModel(private val repository: LectureRepository = Graph.lectureRepository) : ViewModel() {

    var periodState by mutableStateOf("")
    var lectureNameState by mutableStateOf("")
    var timeState by mutableStateOf("")
    var colorState by mutableStateOf("#FFFFFFFF")
    var dayState by mutableStateOf("MONDAY")
    var orderState by mutableStateOf("")



    fun getLecturesForDay(day: String): Flow<List<Lecture>> {
        return repository.getLecturesForDay(day)
    }

    fun insertLecture(lecture: Lecture) {
        viewModelScope.launch {
            repository.insertLecture(lecture)
        }
    }

    fun updateLecture(lecture: Lecture) {
        viewModelScope.launch {
            repository.updateLecture(lecture)
        }
    }

    fun deleteLecture(lecture: Lecture) {
        viewModelScope.launch {
            repository.deleteLecture(lecture)
        }
    }

    fun getLectureById(id: Long): Flow<Lecture> {
        return repository.getLectureById(id)
    }
}
