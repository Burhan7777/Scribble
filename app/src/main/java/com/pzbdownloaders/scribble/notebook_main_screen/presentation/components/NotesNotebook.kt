package com.pzbdownloaders.scribble.notebook_main_screen.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.R
import com.pzbdownloaders.scribble.add_note_feature.domain.model.AddNote
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.locked_notes_feature.presentation.components.SingleItemLockedNoteList
import com.pzbdownloaders.scribble.main_screen.domain.model.Note

@Composable
fun NotesNotebook(
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    navHostController: NavHostController,
    title: String,
    showUnlockDialogBox: MutableState<Boolean>,
) {

    val fontFamilyExtraLight = Font(R.font.lufgaextralight).toFontFamily()

    // viewModel.getNotebookNote(title)
    // val listOfNotes = viewModel.getNotebookNotes.observeAsState().value


    viewModel.getAllNotesByNotebook(title)
    //var listOfNotes = viewModel.listOfNotesByNotebook
    var listOfNotesBooks = remember { SnapshotStateList<Note>() }
    var listOfPinnedNotes = ArrayList<Note>()

    viewModel.listOfNotesByNotebookLiveData.observe(activity) {

        listOfPinnedNotes.clear()
        listOfNotesBooks = it.toMutableStateList()
        for (i in it) {
            if (i.notePinned) {
                listOfPinnedNotes.add(i)
            }
        }
        if (viewModel.listOfLockedNotebooksNote.isEmpty()) {
            // viewModel.listlockedNotes.clear()
            for (i in it) {
                if (i.notebook == title && i.locked) {
                    viewModel.listOfLockedNotebooksNote.add(i)
                }
            }
        }
    }


    if (viewModel.showGridOrLinearNotes.value) {
        LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Adaptive(160.dp)) {
            item(span = StaggeredGridItemSpan.FullLine) {
                Column {
                    Text(
                        text = "My",
                        fontFamily = fontFamilyExtraLight,
                        fontSize = 65.sp,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                    Text(
                        text = title,
                        fontFamily = fontFamilyExtraLight, fontSize = 45.sp,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }

            if (listOfPinnedNotes.size > 0) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    Text(
                        text = "PINNED",
                        fontFamily = FontFamily.fontFamilyBold, fontSize = 15.sp,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
            items(listOfPinnedNotes) { note ->
                SingleItemNotebookList(
                    note = note,
                    navHostController = navHostController,
                    title,
                    viewModel.showLockedNotes
                )
            }
            item(span = StaggeredGridItemSpan.FullLine) {
                Text(
                    text = "ALL NOTES",
                    fontFamily = FontFamily.fontFamilyBold, fontSize = 20.sp,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                )
            }
            items(
                listOfNotesBooks ?: emptyList()
            ) { note ->
                SingleItemNotebookList(
                    note = note,
                    navHostController,
                    title,
                    viewModel.showLockedNotes
                )
            }
            if (!viewModel.showLockedNotes.value && viewModel.listOfLockedNotebooksNote.isNotEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center),

                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Text(
                                if (viewModel.listOfLockedNotebooksNote.size == 1) "1 locked note" else "${viewModel.listOfLockedNotebooksNote.size} locked notes",
                                fontFamily = FontFamily.fontFamilyRegular,
                                fontSize = 15.sp,
                                color = MaterialTheme.colors.onPrimary
                            )
                            TextButton(onClick = {
                                showUnlockDialogBox.value = true
                            }) {
                                Text(
                                    "Show",
                                    fontFamily = FontFamily.fontFamilyBold,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colors.onPrimary,
                                    textDecoration = TextDecoration.Underline
                                )
                            }
                        }
                    }
                }
            } else {
                items(viewModel.listOfLockedNotebooksNote) { note ->
                    SingleItemUnlockNotebookList(
                        note,
                        navHostController,
                        title,
                        viewModel.showLockedNotes
                    )
                }
            }
        }
    } else {
        LazyColumn() {
            item {
                Column {
                    Text(
                        text = "My",
                        fontFamily = fontFamilyExtraLight,
                        fontSize = 65.sp,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                    Text(
                        text = title,
                        fontFamily = fontFamilyExtraLight, fontSize = 45.sp,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
            if (listOfPinnedNotes.size > 0) {
                item {
                    Text(
                        text = "PINNED",
                        fontFamily = FontFamily.fontFamilyBold, fontSize = 15.sp,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
            items(listOfPinnedNotes) { note ->
                SingleItemNotebookList(
                    note = note,
                    navHostController = navHostController,
                    title,
                    viewModel.showLockedNotes
                )
            }
            item {
                Text(
                    text = "ALL NOTES",
                    fontFamily = FontFamily.fontFamilyBold, fontSize = 20.sp,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                )
            }
            items(
                listOfNotesBooks ?: emptyList()
            ) { note ->
                SingleItemNotebookList(
                    note = note,
                    navHostController,
                    title,
                    viewModel.showLockedNotes
                )
            }
            if (!viewModel.showLockedNotes.value && viewModel.listOfLockedNotebooksNote.isNotEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center),

                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Text(
                                if (viewModel.listOfLockedNotebooksNote.size == 1) "1 locked note" else "${viewModel.listOfLockedNotebooksNote.size} locked notes",
                                fontFamily = FontFamily.fontFamilyRegular,
                                fontSize = 15.sp,
                                color = MaterialTheme.colors.onPrimary
                            )
                            TextButton(onClick = {
                                showUnlockDialogBox.value = true
                            }) {
                                Text(
                                    "Show",
                                    fontFamily = FontFamily.fontFamilyBold,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colors.onPrimary,
                                    textDecoration = TextDecoration.Underline
                                )
                            }
                        }
                    }
                }
            } else {
                items(viewModel.listOfLockedNotebooksNote) { note ->
                    SingleItemUnlockNotebookList(
                        note,
                        navHostController,
                        title,
                        viewModel.showLockedNotes
                    )
                }
            }
        }
    }
}
///}



