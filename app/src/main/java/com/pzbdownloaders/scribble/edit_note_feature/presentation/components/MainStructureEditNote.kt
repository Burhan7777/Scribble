package com.pzbdownloaders.scribble.edit_note_feature.presentation.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.add_note_feature.domain.AddNote
import com.pzbdownloaders.scribble.common.domain.utils.GetResult
import com.pzbdownloaders.scribble.common.presentation.*
import java.util.*
import kotlin.collections.HashMap


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainStructureEditNote(
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    id: String,
    activity: MainActivity,
    screen: String
) {
    var context = LocalContext.current

    var dialogOpen = remember {
        mutableStateOf(false)
    }


    var note: AddNote? by remember {
        mutableStateOf(AddNote())
    }

    viewModel.getNoteToEdit(id)
    note = viewModel.getNoteDetailsToEdit.observeAsState().value


    var title by remember {
        mutableStateOf("")
    }


    var content by remember {
        mutableStateOf("")
    }

    if (note != null) {
        title = note?.title ?: ""
        content = note?.content ?: "Failed to get the contents.Please try again"
    }
    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colors.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Undo")
                    }
                },
                title = { Text(text = "") },
                actions = {
                    IconButton(onClick = {
                        //    var updatedNote = Note(id, title, content, getTimeInMilliSeconds(), 123)
                        //   Log.i("title", title)
                        //  viewModel.updateNote(updatedNote)
                        val map = HashMap<String, Any>()
                        map["title"] = title
                        map["content"] = content
                        map["timeStamp"] = System.currentTimeMillis()
                        viewModel.updateNote(note!!.noteId, map)
                        viewModel.getResultFromUpdateNote.observe(activity) {
                            when (it) {
                                is GetResult.Success -> {
                                    Toast.makeText(
                                        context,
                                        "Updated Successfully",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    navController.popBackStack()
                                }
                                is GetResult.Failure -> {
                                    Toast.makeText(
                                        context,
                                        "Update failed. Try again",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        }
                        // Toast.makeText(context, "Note has been updated", Toast.LENGTH_SHORT).show()

                    }) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "Undo")
                    }
                    IconButton(onClick = {
                        if (screen == Constant.HOME) {
                            var hashmap = HashMap<String, Any>()
                            hashmap["archived"] = true
                            viewModel.archiveNotes(id, hashmap)
                            viewModel.getResultFromArchivedNotes.observe(activity) {
                                when (it) {
                                    is GetResult.Success -> {
                                        Toast.makeText(
                                            context,
                                            "Note has be archived",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navController.popBackStack()
                                    }
                                    is GetResult.Failure -> {
                                        Toast.makeText(
                                            context,
                                            "Note failed to be archived",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        } else if (screen == Constant.ARCHIVE) {
                            val hashmap = HashMap<String, Any>()
                            hashmap["archived"] = false
                            viewModel.unArchiveNotes(id, hashmap)
                            viewModel.getResultFromUnArchiveNotes.observe(activity) {
                                when (it) {
                                    is GetResult.Success -> {
                                        Toast.makeText(
                                            context,
                                            "Note has be unarchived",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navController.popBackStack()
                                    }
                                    is GetResult.Failure -> {
                                        Toast.makeText(
                                            context,
                                            "Note failed to be unarchived",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }

                    }) {
                        Icon(
                            imageVector = if (screen == Constant.HOME) Icons.Filled.Archive else Icons.Filled.Unarchive,
                            contentDescription = "Archive"
                        )
                    }
                    IconButton(onClick = {
                        //     viewModel.deleteNote(id)
                        //    Toast.makeText(context, "Note has been deleted", Toast.LENGTH_SHORT).show()
                        //  navController.popBackStack()
                        dialogOpen.value = true

                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            )


        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {

            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            Box(contentAlignment = Alignment.Center) {
                if (dialogOpen.value) {
                    AlertDialogBox(viewModel, id, activity, navController) {
                        dialogOpen.value = false
                    }
                }
            }
            NoteContent(
                title,
                content,
                { title = it },
                { content = it })
        }
    }
}


@Composable
fun AlertDialogBox(
    viewModel: MainActivityViewModel,
    noteId: String,
    activity: MainActivity,
    navHostController: NavHostController,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    androidx.compose.material3.AlertDialog(onDismissRequest = {
        onDismiss()
    },
        shape = MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(15.dp),
            topEnd = CornerSize(15.dp),
            bottomStart = CornerSize(15.dp),
            bottomEnd = CornerSize(15.dp),
        ),
        containerColor = MaterialTheme.colors.primaryVariant,
        /*      icon = {
                     Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
              }*/

        title = {
            Text(
                text = "Delete Note ?",
                fontFamily = FontFamily.fontFamilyBold,
                fontSize = 20.sp
            )
        },
        text = {
            Text(
                text = "Are you sure you want to delete this.It will permanently delete the note forever. ",
                fontFamily = FontFamily.fontFamilyRegular
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.deleteNote(noteId = noteId)
                    viewModel.getResultFromDeleteNote.observe(activity) {
                        when (it) {
                            is GetResult.Success -> {
                                Toast.makeText(
                                    context,
                                    "Note has been deleted",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                onDismiss()
                                navHostController.popBackStack()
                            }
                            is GetResult.Failure -> {
                                Toast.makeText(
                                    context,
                                    "Note could not be deleted. Try again",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.onPrimary,
                    contentColor = MaterialTheme.colors.primary
                ),
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(15.dp),
                    topEnd = CornerSize(15.dp),
                    bottomStart = CornerSize(15.dp),
                    bottomEnd = CornerSize(15.dp),
                )
            ) {
                Text(text = "Delete", fontFamily = FontFamily.fontFamilyRegular)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = { onDismiss() },
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(15.dp),
                    topEnd = CornerSize(15.dp),
                    bottomStart = CornerSize(15.dp),
                    bottomEnd = CornerSize(15.dp),
                ),
                border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    contentColor = MaterialTheme.colors.onPrimary
                ),
            ) {
                Text(text = "Cancel", fontFamily = FontFamily.fontFamilyRegular)
            }
        }

    )
}

