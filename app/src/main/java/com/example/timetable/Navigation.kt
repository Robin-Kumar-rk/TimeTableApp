package com.example.timetable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable

fun Navigation(
    viewModel: LectureViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    currentDateState: MutableState<String>
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) {
            HomeView(navController, viewModel, currentDateState)
        }
        composable(
            Screen.AddLectureScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = -1L  // Change default value to -1L
                    nullable = false
                }
            )
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: -1L  // Use -1L as fallback
            AddEditLectureView(navController, viewModel, id)
        }

    }
}