package com.pzbdownloaders.scribble.add_note_feature.presentation.screens

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.pzbdownloaders.scribble.add_note_feature.domain.model.AddNote
import com.pzbdownloaders.scribble.add_note_feature.presentation.components.MainStructureAddNote
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import java.util.Calendar

@Composable
fun AddNoteScreen(navHostController: NavHostController, viewModel: MainActivityViewModel) {

    var title = remember {
        mutableStateOf("")
    }

    var content = remember {
        mutableStateOf("")
    }

    var notebookState = remember {
        mutableStateOf("")
    }

    //  var note = Note(0, title.value, content.value, getTimeInMilliSeconds(), 123456)
    var note = Note(
        0,
        title.value,
        content.value,
        false,
        notebookState.value,
        timeStamp = 123,

        )
    MainStructureAddNote(navHostController, title, content, viewModel, note, notebookState)


}

fun getTimeInMilliSeconds(): Long {
    var calendar = Calendar.getInstance()
    return calendar.timeInMillis
}