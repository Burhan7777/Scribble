package com.pzbdownloaders.add_bullet_points_note_feature.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import com.pzbdownloaders.add_bullet_points_note_feature.presentation.components.MainStructureBulletPointsNotes
import com.pzbdownloaders.scribble.add_checkbox_note_feature.presentation.components.MainStructureCheckBoxNote
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.main_screen.presentation.components.MainStructureMainScreen

@Composable
fun BulletPointsNoteMainScreen(
    navHostController: NavHostController,
    mainActivityViewModel: MainActivityViewModel,
    activity: MainActivity
) {

    var notebookState = rememberSaveable {
        mutableStateOf("")
    }

    var title = rememberSaveable {
        mutableStateOf("")
    }

    MainStructureBulletPointsNotes(
        navController = navHostController,
        viewModel = mainActivityViewModel,
        notebookState,
        title,
        activity = activity
    )
}