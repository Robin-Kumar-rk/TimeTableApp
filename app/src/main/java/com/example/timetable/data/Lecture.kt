package com.example.timetable.data
import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lecture_table")
data class Lecture(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "day") val day: String = "", // Day like "MONDAY"
    @ColumnInfo(name = "period") val period: String = "", // Period like "1"
    @ColumnInfo(name = "lecture_name") val lectureName: String = "", // Name of the lecture
    @ColumnInfo(name = "time") val time: String = "", // Time like "8:50-10:30",
    @ColumnInfo(name = "color") val color: String = "0xFF0000FF",// Color of the lecture
    @ColumnInfo(name = "order") val order: String = "0"
) {
    fun getColor(): Color = Color(android.graphics.Color.parseColor(color))
}

