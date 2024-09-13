package com.pzbdownloaders.scribble.notebook_main_screen.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.R
import com.pzbdownloaders.scribble.add_note_feature.domain.model.AddNote
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.main_screen.domain.model.Note

@Composable
fun NotesNotebook(
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    navHostController: NavHostController,
    title: String
) {

    val fontFamilyExtraLight = Font(R.font.lufgaextralight).toFontFamily()

    // viewModel.getNotebookNote(title)
    // val listOfNotes = viewModel.getNotebookNotes.observeAsState().value


    viewModel.getAllNotesByNotebook(title)
    //var listOfNotes = viewModel.listOfNotesByNotebook
    var listOfNotesBooks = SnapshotStateList<Note>()
    viewModel.listOfNotesByNotebookLiveData.observe(activity) {
        listOfNotesBooks = it.toMutableStateList()
        println(listOfNotesBooks.size)
    }


    LazyColumn() {
        item {
            Text(
                text = "My",
                fontFamily = fontFamilyExtraLight,
                fontSize = 65.sp,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
        item {
            Text(
                text = title,
                fontFamily = fontFamilyExtraLight, fontSize = 65.sp,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

        }
        items(
            listOfNotesBooks ?: emptyList()
        ) { note ->
            SingleItemNotebookList(note = note, navHostController, title)
        }
    }
}
///}



