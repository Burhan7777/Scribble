package com.pzbdownloaders.scribble.add_note_screen.presentation.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.add_note_screen.domain.AddNote
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.main_screen.domain.model.Note

@Composable
fun MainStructureAddNote(
    navController: NavHostController,
    title: MutableState<String>,
    content: MutableState<String>,
    viewModel: MainActivityViewModel,
    note: AddNote
) {
    var context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.primary,
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Undo")
                }

                /*   IconButton(onClick = { *//*TODO*//* }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Undo")
                }
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Redo")
                }*/
                Spacer(modifier = Modifier.width(270.dp))
                IconButton(onClick = {
                    //     viewModel.insertNote(note)
                    viewModel.addNoteToCloud(note)
                    Toast.makeText(context, "Note has been added", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = "Save")
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {

            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            NoteContent(title, content)
        }
    }
}