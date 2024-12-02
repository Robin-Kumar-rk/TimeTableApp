package com.example.timetable


import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding


//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppBarView(
    title: String,
    onBackClicked: () -> Unit = {},
) {
    val navigationIcon: @Composable (() -> Unit)? =
        if (!title.contains("Time Table")) {
            {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }
        } else null
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp).heightIn(max = 24.dp)
            )
        },
        elevation = 3.dp,
        backgroundColor = Color.White,
        navigationIcon = navigationIcon
    )
}