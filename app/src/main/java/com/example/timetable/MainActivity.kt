package com.example.timetable

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.timetable.ui.theme.TimeTableTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : ComponentActivity() {

    private lateinit var dateChangeReceiver: BroadcastReceiver
    val currentDateState = mutableStateOf(getCurrentDay())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TimeTableTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    Navigation(currentDateState = currentDateState)
                }
            }
        }

        dateChangeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                currentDateState.value = getCurrentDay()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(Intent.ACTION_TIME_CHANGED).apply {
            addAction(Intent.ACTION_DATE_CHANGED)
        }
        registerReceiver(dateChangeReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(dateChangeReceiver)
    }
}
fun getCurrentDay(): String {
    val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault()) // Use "EEEE" for full day names
    return dayFormat.format(Date()).uppercase()
}










