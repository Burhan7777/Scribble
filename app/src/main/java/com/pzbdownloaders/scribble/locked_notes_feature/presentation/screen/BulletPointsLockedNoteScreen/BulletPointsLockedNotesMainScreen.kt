package com.pzbdownloaders.scribble.locked_notes_feature.presentation.screen.BulletPointsLockedNoteScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.pzbdownloaders.add_bullet_points_note_feature.presentation.components.MainStructureBulletPointsNotes
import com.pzbdownloaders.scribble.add_checkbox_note_feature.presentation.components.MainStructureCheckBoxNote
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.locked_notes_feature.presentation.components.BulletPointsLockedNotesComponents.MainStructureBulletPointsLockedNotes
import com.pzbdownloaders.scribble.main_screen.presentation.components.MainStructureMainScreen
import com.pzbdownloaders.scribble.notebook_main_screen.presentation.components.bulletpointsScreenComponents.MainStructureBulletPointsNotebook

@Composable
fun BulletPointsLockedNotesMainScreen(
    navHostController: NavHostController,
    mainActivityViewModel: MainActivityViewModel,
    activity: MainActivity
) {

    var notebookState = remember {
        mutableStateOf("")
    }

    var title = remember {
        mutableStateOf("")
    }

    MainStructureBulletPointsLockedNotes(
        navController = navHostController,
        viewModel = mainActivityViewModel,
        notebookState,
        title,
        activity = activity
    )
}