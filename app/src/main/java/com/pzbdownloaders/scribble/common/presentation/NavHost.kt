package com.pzbdownloaders.scribble.common.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pzbdownloaders.scribble.add_note_screen.presentation.screens.AddNoteScreen
import com.pzbdownloaders.scribble.main_screen.presentation.screens.NotesScreen
import com.pzbdownloaders.scribble.common.presentation.Screens
import com.pzbdownloaders.scribble.edit_screen.presentation.screens.EditNoteScreen
import com.pzbdownloaders.scribble.login_and_signup.authGraph

@Composable
fun NavHost(
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    result: String
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = if (result == Constant.USER_VALUE) HOME_GRAPH else AUTH_GRAPH
    ) {
        authGraph(navHostController = navController, viewModel, activity, result)
        homeGraph(navController = navController, viewModel = viewModel, activity = activity,result)
    }
}