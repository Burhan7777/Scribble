package com.pzbdownloaders.scribble.common.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.pzbdownloaders.scribble.about_us.presentation.screens.AboutUsScreen
import com.pzbdownloaders.scribble.add_checkbox_note_feature.presentation.screens.CheckboxNoteMainScreen
import com.pzbdownloaders.scribble.add_note_feature.presentation.screens.AddNoteScreen
import com.pzbdownloaders.scribble.archive_notes_feature.presentation.screen.ArchiveNotesScreen
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.edit_note_feature.presentation.screens.EditNoteScreen
import com.pzbdownloaders.scribble.locked_notes_feature.presentation.LockedNotesScreen
import com.pzbdownloaders.scribble.main_screen.presentation.screens.NotesScreen
import com.pzbdownloaders.scribble.notebook_main_screen.presentation.screen.NotebookMainScreen
import com.pzbdownloaders.scribble.search_feature.presentation.screens.SearchScreen
import com.pzbdownloaders.scribble.settings_feature.screen.presentation.screens.SettingsScreen


fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    result: String,
    selectedItem: MutableState<Int>,
    selectedNote: MutableState<Int>
) {
    navigation(
        startDestination = if (result == Constant.USER_VALUE) Screens.SplashScreen.route else Screens.HomeScreen.route,
        route = HOME_GRAPH
    ) {
        if (result == Constant.USER_VALUE) {
            composable(route = Screens.SplashScreen.route) {
                SplashScreen(result, navController)
            }
        }
        composable(Screens.HomeScreen.route) {
            NotesScreen(navController, viewModel, activity, selectedItem, selectedNote)
        }
        composable(Screens.AddNoteScreen.route) {
            AddNoteScreen(navController, viewModel)
        }
        composable(
            Screens.EditNoteScreen.route,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            }, navArgument("screen") {
                type = NavType.StringType
            }
            )) {
            Log.i("notesId", it.arguments!!.getString("id").toString())
            EditNoteScreen(
                navHostController = navController,
                viewModel = viewModel,
                activity = activity,
                id = it.arguments!!.getInt("id")!!,
                screen = it.arguments!!.getString("screen")!!
            )
        }
        composable(route = Screens.SettingsScreen.route) {
            SettingsScreen(navController, activity)
        }
        composable(Screens.ArchiveScreen.route) {
            ArchiveNotesScreen(
                navHostController = navController,
                viewModel = viewModel,
                activity = activity,
                selectedItem,
                selectedNote
            )
        }

        composable(Screens.SearchScreen.route,
            listOf(navArgument("screen") {
                type = NavType.StringType
            }
            )) {
            SearchScreen(
                navHostController = navController,
                viewModel = viewModel,
                activity = activity,
                screen = it.arguments?.getString("screen")!!
            )
        }

        composable(Screens.AboutUsScreen.route) {
            AboutUsScreen(activity)
        }

        composable(
            Screens.NotebookMainScreen.route,
            listOf(navArgument("title") {
                type = NavType.StringType
            })
        ) {
            NotebookMainScreen(
                navController,
                viewModel,
                activity,
                it.arguments?.getString("title")!!,
                selectedItem,
                selectedNote
            )
        }
        composable(Screens.CheckboxMainScreen.route) {
            CheckboxNoteMainScreen(navController, viewModel,activity)
        }
        composable(Screens.LockedNotesScreen.route) {
            LockedNotesScreen(
                navHostController = navController,
                viewModel = viewModel,
                activity = activity,
                selectedItem = selectedItem,
                selectedNote = selectedNote
            )
        }
    }
}