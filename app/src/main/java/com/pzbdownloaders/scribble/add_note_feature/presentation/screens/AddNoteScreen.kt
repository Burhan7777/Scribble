package com.pzbdownloaders.scribble.add_note_feature.presentation.screens

import android.text.Html
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.pzbdownloaders.scribble.add_note_feature.domain.model.AddNote
import com.pzbdownloaders.scribble.add_note_feature.presentation.components.MainStructureAddNote
import com.pzbdownloaders.scribble.common.data.Model.NoteBook
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import java.util.Calendar

@Composable
fun AddNoteScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity
) {

    var title = rememberSaveable {
        mutableStateOf("")
    }

    var content = remember {
        mutableStateOf("")
    }

    var notebookState = remember {
        mutableStateOf("")
    }

    val richStateText = mutableStateOf(rememberRichTextState())

    viewModel.getAllNotebooks()

    //  var note = Note(0, title.value, content.value, getTimeInMilliSeconds(), 123456)


    MainStructureAddNote(
        navHostController,
        title = title,
        content = content,
        viewModel = viewModel,
        //note,
        notebookState = notebookState,
        activity = activity,
        richTextState = richStateText
//        notebook,
//        notebookFromDB
    )


}

fun getTimeInMilliSeconds(): Long {
    var calendar = Calendar.getInstance()
    return calendar.timeInMillis
}