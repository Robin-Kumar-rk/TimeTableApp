package com.example.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.automirrored.filled.ArrowForward


import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.colorResource

import androidx.compose.ui.unit.dp

@Composable
fun Buttons(onOptionSelected: (String) -> Unit, selectedOption: String, currDay: String ) {
    val selectedColor = Color(0xFFEC3939)
    val buttonColor = Color(0xF3FFE893)
    Box(
        modifier = Modifier
            .fillMaxWidth() // Make the outer box fill the width
            .background(
                color = Color.White, // Light background color for the outer box
                shape = RoundedCornerShape(12.dp) // Rounded corners for the outer box
            )
            .padding(4.dp) // Padding inside the outer box
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onOptionSelected("MONDAY") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedOption == "MONDAY") selectedColor else buttonColor,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text("M", color = Color.Black)
                }
                Button(
                    onClick = { onOptionSelected("TUESDAY") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedOption == "TUESDAY") selectedColor else buttonColor,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text("T", color = Color.Black)
                }
                Button(
                    onClick = { onOptionSelected("WEDNESDAY") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedOption == "WEDNESDAY") selectedColor else buttonColor,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text("W", color = Color.Black)
                }
                Button(
                    onClick = { onOptionSelected("THURSDAY") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedOption == "THURSDAY") selectedColor else buttonColor,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text("T", color = Color.Black)
                }
                Button(
                    onClick = { onOptionSelected("FRIDAY") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedOption == "FRIDAY") selectedColor else buttonColor,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text("F", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Button for "Today"
                Button(
                    onClick = { onOptionSelected(currDay) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedOption == currDay) selectedColor else buttonColor,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                ) {
                    Text("Today", color = Color.Black)
                }
                Button(
                    onClick = { onOptionSelected(nextDay[selectedOption].toString()) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                        if (selectedOption == nextDay[selectedOption]) selectedColor
                        else buttonColor,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,  // You can also use ArrowForward
                        contentDescription = "Right Arrow",
                        tint = Color.Black  // Set the color to black
                    )

                }

                // Button for "Next Day"
                Button(
                    onClick = { nextDay[currDay]?.let { onOptionSelected(it) } },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedOption == nextDay[currDay]) selectedColor else buttonColor,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                ) {
                    Text("Next", color = Color.Black)
                }
            }
        }
    }
}
@Composable

fun SpecialButton(dayName: String) {
    Spacer(modifier = Modifier.padding(16.dp))
    var text by remember { mutableStateOf(dayName) }
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button (onClick = {
            text = if (text == dayName) "Moj" else dayName
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.button_default),
                contentColor = colorResource(R.color.button_selection)
            )){
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF000000)
            )
        }
    }
}
