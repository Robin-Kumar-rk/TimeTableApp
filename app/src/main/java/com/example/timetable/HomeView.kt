package com.example.timetable

import android.annotation.SuppressLint

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.timetable.data.Lecture
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(
    navController: NavController,
    viewModel: LectureViewModel,
    currentDateState: MutableState<String>
) {
    var selectedOptionState by remember { mutableStateOf(getDefaultSelectedOption().toString())}
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(20.dp),
                contentColor = Color.Black,
                containerColor = Color.White,
                shape = RoundedCornerShape(30.dp),
                onClick = {
                    navController.navigate(Screen.AddLectureScreen.route + "/-1L")
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        containerColor = Color.Black
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(color = Color.Black)
                .padding(it)
                .padding(start = 8.dp, end = 8.dp)
        ) {

            Title()

            Buttons({ selectedOptionState = it }, selectedOptionState, currentDateState.value)

            Spacer(modifier = Modifier.padding(8.dp))

            val lectureList = viewModel.getLecturesForDay(selectedOptionState)
                .collectAsState(initial = emptyList())
                .value
                .sortedBy { it.order.toIntOrNull() ?: 0 }  // Sorting by 'order' as integer, defaulting to 0 if conversion fails


            Text(
                text = selectedOptionState,
                style = MaterialTheme.typography.titleLarge.copy(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Red,
                            Color(0xFF00FFAB),
                            Color(0xFF0048FF)
                        ), // Gradient colors
                        start = Offset.Zero,
                        end = Offset.Infinite
                    ),
                ),
                fontSize = 20.sp, // Font size for the text
                modifier = Modifier.align(Alignment.CenterHorizontally)// Align the text in the center
            )
            if (selectedOptionState == "SATURDAY" || selectedOptionState == "SUNDAY") {
                SpecialButton(dayName = selectedOptionState)
            }
            Spacer(modifier = Modifier.padding(8.dp))

            if (lectureList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(16.dp)
                        ) // Rounded corners
                        .padding(8.dp)  // Inner padding inside the background
                        .clip(RoundedCornerShape(16.dp)) // Ensures content stays within rounded corners
                ) {
                    items(lectureList, key = { lecture -> lecture.id }) { lecture ->
                        val dismissState = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                                    viewModel.deleteLecture(lecture)
                                }
                                true
                            }
                        )
                        SwipeToDismiss(
                            state = dismissState,
                            background = {
                                val color by animateColorAsState(
                                    if (dismissState.dismissDirection
                                        == DismissDirection.EndToStart
                                    ) Color.Red else Color.Transparent,
                                    label = ""
                                )
                                val alignment = Alignment.CenterEnd
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(horizontal = 20.dp),
                                    contentAlignment = alignment
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.White
                                    )
                                }
                            },
                            directions = setOf(DismissDirection.EndToStart),
                            dismissThresholds = { FractionalThreshold(0.555f) }

                        ) {
                            LectureCard(lecture = lecture) {
                                navController.navigate(Screen.AddLectureScreen.route + "/${lecture.id}")
                            }
                        }
                    }
                }
            }
        }


    }
}

@Composable
fun LectureCard(lecture: Lecture, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Make each card fill the width
            .padding(vertical = 4.dp) // Add vertical padding between cards
            .clickable { onClick() }, // Make the card clickable
        shape = RoundedCornerShape(12.dp), // Rounded corners for each card
        colors = CardDefaults.cardColors(
            containerColor = lecture.getColor() // Light background color for each card
        ),
    ) {
        // Row for the lecture details inside the card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp), // Padding inside each card
            horizontalArrangement = Arrangement.SpaceBetween, // Even space between text elements
            verticalAlignment = Alignment.CenterVertically // Center vertically
        ) {
            Text(
                text = lecture.period,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.W700
            )
            Text(
                text = lecture.lectureName,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.W700
            )
            Text(
                text = lecture.time,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.W700
            )
        }
    }
}

@Composable
fun Title() {
    Box(
        modifier = Modifier
            .fillMaxWidth() // Make the Box take up the full width
            .padding(8.dp) // Add padding around the Box
            .background(
                color = Color(0xFF000000), // Set background to black
                shape = RoundedCornerShape(12.dp) // Rounded corners
            )
            .shadow(8.dp, RoundedCornerShape(12.dp)) // Add shadow for depth
            .padding(8.dp)
        , // Padding inside the Box
        contentAlignment = Alignment.Center // Center the text inside the Box
    ) {
        // Styled Timetable Text
        Text(
            text = "Timetable",
            style = MaterialTheme.typography.titleLarge.copy(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Red, Color(0xFF00FFAB), Color(0xFF0048FF)), // Gradient colors
                    start = Offset.Zero,
                    end = Offset.Infinite
                ),
                letterSpacing = 1.5.sp // Increase letter spacing
            ),
            fontSize = 36.sp, // Font size for the text
            modifier = Modifier.align(Alignment.Center) // Align the text in the center
        )
    }
}

