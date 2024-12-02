package com.example.timetable

import android.widget.Toast
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.timetable.data.Lecture
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext


@Composable

fun AddEditLectureView(
    navController: NavController,
    viewModel: LectureViewModel,
    id: Long
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val selectedColor = remember { mutableStateOf(Color.White) }
    val context = LocalContext.current

    if (id != -1L) {
        val lecture = viewModel.getLectureById(id)
            .collectAsState(initial = Lecture(0L, "MONDAY", "", "", "", "#FFFFFFFF", "0"))
        viewModel.periodState = lecture.value.period
        viewModel.lectureNameState = lecture.value.lectureName
        viewModel.timeState = lecture.value.time
        viewModel.colorState = lecture.value.color
        viewModel.dayState = lecture.value.day
        viewModel.orderState = lecture.value.order
    } else {
        viewModel.periodState = ""
        viewModel.lectureNameState = ""
        viewModel.timeState = ""
        viewModel.colorState = "#FFFFFFFF"
        viewModel.dayState = "MONDAY"
        viewModel.orderState = "0"
    }

    Scaffold(
        topBar = {
            AppBarView(title = if (id == -1L) "Add Lecture" else "Edit Lecture") {
                navController.popBackStack()
            }
        },
        scaffoldState = scaffoldState,
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.padding(8.dp))

            LectureTextField(
                label = "Period",
                value = viewModel.periodState,
                onValueChange = { viewModel.periodState = it }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            LectureTextField(
                label = "Lecture/Subject/Title",
                value = viewModel.lectureNameState,
                onValueChange = { viewModel.lectureNameState = it }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            LectureTextField(
                label = "Time",
                value = viewModel.timeState,
                onValueChange = { viewModel.timeState = it }
            )

            Spacer(modifier = Modifier.padding(8.dp))


            DayDropdownMenu(viewModel)

            Spacer(modifier = Modifier.padding(8.dp))

            LectureTextField(
                label = "Order in list",
                value = viewModel.orderState.toString(),
                onValueChange = { viewModel.orderState = it}
            )

            ColorGradientPalette { color ->
                selectedColor.value = color
                viewModel.colorState = "#${color.toArgb().toUInt().toString(16)}"
            }


            Button(
                onClick = {
                    if (viewModel.lectureNameState.isNotEmpty()) {
                        if (id != -1L) {
                            viewModel.updateLecture(
                                Lecture(
                                    id = id,
                                    day = viewModel.dayState,
                                    period = viewModel.periodState,
                                    lectureName = viewModel.lectureNameState,
                                    time = viewModel.timeState,
                                    color = viewModel.colorState,
                                    order = viewModel.orderState
                                )
                            )
                        } else {
                            viewModel.insertLecture(
                                Lecture(
                                    day = viewModel.dayState,
                                    period = viewModel.periodState,
                                    lectureName = viewModel.lectureNameState,
                                    time = viewModel.timeState,
                                    color = viewModel.colorState,
                                    order = viewModel.orderState
                                )
                            )
                        }
                    } else {
                        Toast.makeText(context, "Title and day name must be filled", Toast.LENGTH_SHORT).show()
                    }
                    scope.launch {
                        navController.popBackStack()
                    }
                }
            ) {
                Text(
                    text = if (id == -1L) "Add Lecture" else "Update Lecture",
                    style = TextStyle(
                        color = Color.White, fontSize = 18.sp,
                    )
                )
            }
        }
    }
}


@Composable

fun LectureTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label, color = colorResource(id = R.color.black))
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = colorResource(id = R.color.black),
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.black),
        ),
        textStyle = TextStyle(color = colorResource(id = R.color.black))
    )
}


@Composable
fun ColorGradientPalette(onColorClicked: (Color) -> Unit) {
    val colors = listOf(
        Color.Red, Color.Yellow, Color.Green, Color.Cyan, Color.Blue, Color.Magenta,
        Color.White, Color.Black
    )

    var selectedColor by remember { mutableStateOf(Color.Transparent) }
    var pointerPosition by remember { mutableStateOf<Offset?>(null) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent() // Awaiting pointer events
                        val position = event.changes.firstOrNull()?.position
                        position?.let { offset ->
                            pointerPosition = offset
                            val width = size.width
                            val tappedFraction = offset.x / width
                            val gradientColor = interpolateColor(colors, tappedFraction)
                            selectedColor = gradientColor
                            onColorClicked(gradientColor)
                        }
                    }
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw gradient
            drawRect(
                brush = Brush.horizontalGradient(colors)
            )

            // Draw the hollow circular pointer
            pointerPosition?.let { offset ->
                drawCircle(
                    color = Color.White,
                    radius = 8.dp.toPx(), // Outer circle size
                    center = offset,
                    style = Stroke(width = 2.dp.toPx()) // Set border thickness
                )
            }
        }

        // Display the selected color for feedback (optional)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(selectedColor)
        )
    }
}

fun interpolateColor(colors: List<Color>, fraction: Float): Color {
    val colorCount = colors.size - 1
    val scaledFraction = fraction.coerceIn(0f, 1f) * colorCount
    val index = scaledFraction.toInt()
    val startColor = colors[index]
    val endColor = colors[(index + 1).coerceAtMost(colorCount)]

    val localFraction = scaledFraction - index
    return Color(
        red = startColor.red + (endColor.red - startColor.red) * localFraction,
        green = startColor.green + (endColor.green - startColor.green) * localFraction,
        blue = startColor.blue + (endColor.blue - startColor.blue) * localFraction,
        alpha = startColor.alpha + (endColor.alpha - startColor.alpha) * localFraction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayDropdownMenu(viewModel: LectureViewModel) {
    val daysOfWeek = listOf("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = viewModel.dayState,
            onValueChange = {},
            label = { Text("Day") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(), // Ensures dropdown aligns with TextField
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon"
                )
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            daysOfWeek.forEach { day ->
                DropdownMenuItem(
                    onClick = {
                        viewModel.dayState = day
                        expanded = false
                    },

                ) {
                     Text(text = day)
                }
            }
        }
    }
}

