package com.pzbdownloaders.scribble.common.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.pzbdownloaders.add_bullet_points_note_feature.presentation.screens.BulletPointsNoteMainScreen
import com.pzbdownloaders.scribble.about_us.presentation.screens.AboutUsScreen
import com.pzbdownloaders.scribble.add_checkbox_note_feature.presentation.screens.CheckboxNoteMainScreen
import com.pzbdownloaders.scribble.add_note_feature.presentation.screens.AddNoteScreen
import com.pzbdownloaders.scribble.archive_notes_feature.presentation.screen.ArchiveNotesScreen
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.edit_note_feature.presentation.screens.EditNoteScreen
import com.pzbdownloaders.scribble.locked_notes_feature.presentation.screen.AddNoteInLockedScreen
import com.pzbdownloaders.scribble.locked_notes_feature.presentation.screen.LockedNotesScreen
import com.pzbdownloaders.scribble.main_screen.presentation.screens.NotesScreen
import com.pzbdownloaders.scribble.notebook_main_screen.presentation.screen.AddNoteInNotebookScreen
import com.pzbdownloaders.scribble.notebook_main_screen.presentation.screen.NotebookMainScreen
import com.pzbdownloaders.scribble.notebook_main_screen.presentation.screen.checkboxscreen.CheckboxNoteBookMainScreen
import com.pzbdownloaders.scribble.settings_feature.screen.presentation.screens.BackupAndRestoreScreen
import com.pzbdownloaders.scribble.search_main_screen_feature.presentation.screens.SearchScreen
import com.pzbdownloaders.scribble.settings_feature.screen.presentation.screens.SettingsScreen
import com.pzbdownloaders.trash_bin_feature.presentation.screens.DeleteTrashScreen
import com.pzbdownloaders.trash_bin_feature.presentation.screens.TrashBinScreen


fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    result: String,
    selectedItem: MutableState<Int>,
    selectedNote: MutableState<Int>
) {
    navigation(
        startDestination = Screens.HomeScreen.route,
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
            AddNoteScreen(navController, viewModel, activity)
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

        composable(
            Screens.SearchScreen.route,
            listOf(navArgument("screen") {
                type = NavType.StringType
            }, navArgument("queryText") {
                type = NavType.StringType
            })
        ) {
            SearchScreen(
                navHostController = navController,
                viewModel = viewModel,
                activity = activity,
                screen = it.arguments?.getString("screen")!!,
                queryText = it.arguments?.getString("queryText")!!
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
            CheckboxNoteMainScreen(navController, viewModel, activity)
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
        composable(Screens.BulletPointMainScreen.route) {
            BulletPointsNoteMainScreen(navController, viewModel, activity)
        }
        composable(Screens.TrashBinScreen.route) {
            TrashBinScreen(navController, viewModel, activity, selectedItem, selectedNote)
        }
        composable(Screens.DeleteTrashScreen.route, listOf(navArgument("id") {
            type = NavType.IntType
            defaultValue = 0
        })) {
            DeleteTrashScreen(navController, viewModel, activity, it.arguments?.getInt("id") ?: 0)
        }

        composable(Screens.AddNoteInNotebookScreen.route, listOf(navArgument("notebookName") {
            type = NavType.StringType
        })) {
            AddNoteInNotebookScreen(
                viewModel,
                navController,
                activity,
                it.arguments?.getString("notebookName")!!
            )
        }
        composable(Screens.AddNoteInLockedScreen.route) {
            AddNoteInLockedScreen(
                viewModel = viewModel,
                navHostController = navController,
                activity = activity
            )
        }

        composable(Screens.BackupAndRestoreScreen.route) {
            BackupAndRestoreScreen()
        }
        composable(Screens.CheckboxNotebookMainScreen.route, listOf(navArgument("notebook") {
            type = NavType.StringType
        })) {
            CheckboxNoteBookMainScreen(
                navHostController = navController,
                mainActivityViewModel = viewModel,
                activity = activity,
                it.arguments?.getString("notebook")!!
            )
        }


    }
}