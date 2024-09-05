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
import com.pzbdownloaders.scribble.add_note_feature.domain.model.AddNote
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.domain.utils.GetResult
import com.pzbdownloaders.scribble.common.presentation.*
import com.pzbdownloaders.scribble.edit_note_feature.domain.usecase.checkIfUserHasCreatedPassword
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import java.util.*
import kotlin.collections.HashMap


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainStructureEditNote(
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    id: Int,
    activity: MainActivity,
    screen: String
) {
    var context = LocalContext.current

    var dialogOpen = remember {
        mutableStateOf(false)
    }


//    var note: AddNote? by remember {
//        mutableStateOf(AddNote())
//    }

    //viewModel.getNoteToEdit(id)
    // note = viewModel.getNoteDetailsToEdit.observeAsState().value

    viewModel.getNoteById(id)
    var note = viewModel.getNoteById


    var title by remember {
        mutableStateOf("")
    }


    var content by remember {
        mutableStateOf("")
    }

    var notebook by remember {
        mutableStateOf("")
    }

    var isExpanded = remember {
        mutableStateOf(false)
    }

    var selectedNotebook = remember {
        mutableStateOf("")
    }

    var passwordNotSetUpDialogBox = remember {
        mutableStateOf(false)
    }

    var enterPasswordToLockDialogBox = remember { mutableStateOf(false) }

    var enterPasswordToUnLockDialogBox = remember { mutableStateOf(false) }


    LaunchedEffect(key1 = true) {
        title = note.value.title ?: ""
        content = note.value.content ?: "Failed to get the contents.Please try again"
        notebook = note.value.notebook
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
//                        val map = HashMap<String, Any>()
//                        map["title"] = title
//                        map["content"] = content
//                        map["timeStamp"] = System.currentTimeMillis()
//                        if (selectedNotebook.value.isNotEmpty()) map["label"] =
//                            selectedNotebook.value
//                      //  viewModel.updateNote(note!!.noteId, map)
//                        viewModel.getResultFromUpdateNote.observe(activity) {
//                            when (it) {
//                                is GetResult.Success -> {
//                                    Toast.makeText(
//                                        context,
//                                        "Updated Successfully",
//                                        Toast.LENGTH_SHORT
//                                    )
//                                        .show()
//                                    navController.popBackStack()
//                                }
//                                is GetResult.Failure -> {
//                                    Toast.makeText(
//                                        context,
//                                        "Update failed. Try again",
//                                        Toast.LENGTH_SHORT
//                                    )
//                                        .show()
//                                }
//                            }
//                        }
                        viewModel.getNoteById(id)
                        var noteFromDb = viewModel.getNoteById
                        var archived = noteFromDb.value.archive
                        var note = Note(
                            id,
                            title,
                            content,
                            archived,
                            notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,
                            timeStamp = 123
                        )
                        viewModel.updateNote(note)
                        navController.popBackStack()
                        Toast.makeText(context, "Note has been updated", Toast.LENGTH_SHORT).show()

                    }) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "Undo")
                    }
                    IconButton(onClick = {
                        if (screen == Constant.HOME || screen == Constant.ARCHIVE) {
                            val result = checkIfUserHasCreatedPassword()
                            result.observe(activity) {
                                if (it == false) {
                                    passwordNotSetUpDialogBox.value = true
                                } else {
                                    enterPasswordToLockDialogBox.value = true

                                }
                            }


                        } else if (screen == Constant.LOCKED_NOTE) {
                            enterPasswordToUnLockDialogBox.value = true
                        }
                    }) {
                        Icon(
                            imageVector = if (screen ==
                                Constant.HOME
                            ) Icons.Filled.Lock else if (screen == Constant.ARCHIVE) Icons.Filled.Lock else Icons.Filled.LockOpen,
                            contentDescription = "Lock Note"
                        )
                    }
                    IconButton(onClick = {
                        if (screen == Constant.HOME || screen == Constant.LOCKED_NOTE) {

                            var note = (Note(id, title, content, true, timeStamp = 123))
                            viewModel.updateNote(note)
                            Toast.makeText(activity, "Note has been archived", Toast.LENGTH_SHORT)
                                .show()
                            navController.popBackStack()
//                            var hashmap = HashMap<String, Any>()
//                            hashmap["archived"] = true
                            //   viewModel.archiveNotes(id, hashmap)
//                            viewModel.getResultFromArchivedNotes.observe(activity) {
//                                when (it) {
//                                    is GetResult.Success -> {
//                                        Toast.makeText(
//                                            context,
//                                            "Note has be archived",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                        navController.popBackStack()
//                                    }
//
//                                    is GetResult.Failure -> {
//                                        Toast.makeText(
//                                            context,
//                                            "Note failed to be archived",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
//                                }
//                            }
                        } else if (screen == Constant.ARCHIVE) {
                            var note = (Note(id, title, content, false, timeStamp = 123))
                            viewModel.updateNote(note)
                            Toast.makeText(activity, "Not has been unarchived", Toast.LENGTH_SHORT)
                                .show()
                            navController.popBackStack()
//                            val hashmap = HashMap<String, Any>()
//                            hashmap["archived"] = false
                            // viewModel.unArchiveNotes(id, hashmap)
//                            viewModel.getResultFromUnArchiveNotes.observe(activity) {
//                                when (it) {
//                                    is GetResult.Success -> {
//                                        Toast.makeText(
//                                            context,
//                                            "Note has been unarchived",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                        navController.popBackStack()
//                                    }
//
//                                    is GetResult.Failure -> {
//                                        Toast.makeText(
//                                            context,
//                                            "Note failed to be unarchived",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
//                                }
//                            }
                        }

                    }) {
                        Icon(
                            imageVector = if (screen == Constant.HOME) Icons.Filled.Archive else if (screen == Constant.LOCKED_NOTE) Icons.Filled.Archive else Icons.Filled.Unarchive,
                            contentDescription = "Archive"
                        )
                    }
                    IconButton(onClick = {
                        // viewModel.deleteNoteById(id)
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
//                    AlertDialogBox(viewModel, id, activity, navController) {
//                        dialogOpen.value = false
//                    }
                }
            }
            NoteContent(
                selectedNotebook,
                isExpanded,
                viewModel,
                title,
                content,
                notebook,
                { title = it },
                { content = it })
        }
    }
    if (dialogOpen.value) {
        AlertDialogBoxDelete(
            viewModel = viewModel,
            id = id,
            activity = activity,
            navHostController = navController
        ) {
            dialogOpen.value = false
        }
    }
    if (passwordNotSetUpDialogBox.value) {
        AlertDialogBoxPassword(
            viewModel = viewModel,
            activity = activity,
            navHostController = navController
        ) {
            passwordNotSetUpDialogBox.value = false
        }
    }
    if (enterPasswordToLockDialogBox.value) {
        AlertDialogBoxEnterPassword(
            viewModel = viewModel,
            id = id,
            activity = activity,
            navHostController = navController,
            title = title,
            content = content
        ) {
            enterPasswordToLockDialogBox.value = false
        }
    }
    if(enterPasswordToUnLockDialogBox.value){
        AlertDialogBoxEnterPasswordToUnlock(
            viewModel = viewModel,
            id = id,
            activity = activity,
            navHostController = navController,
            title = title,
            content = content
        ) {
            
        }
    }
}





