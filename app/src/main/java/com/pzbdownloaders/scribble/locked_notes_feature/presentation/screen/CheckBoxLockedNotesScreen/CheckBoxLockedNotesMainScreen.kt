package com.pzbdownloaders.scribble.locked_notes_feature.presentation.screen.CheckBoxLockedNotesScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.add_checkbox_note_feature.presentation.components.MainStructureCheckBoxNote
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.locked_notes_feature.presentation.components.ChecboxLockedNotesComponents.MainStructureCheckBoxLockedNotes
import com.pzbdownloaders.scribble.notebook_main_screen.presentation.components.checkboxScreenComponents.MainStructureCheckBoxNotebook

@Composable
fun CheckboxLockedNotesMainScreen(
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

    MainStructureCheckBoxLockedNotes(
        navController = navHostController,
        viewModel = mainActivityViewModel,
        notebookState,
        title,
        activity = activity
    )
}