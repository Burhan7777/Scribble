package com.pzbdownloaders.scribble.main_screen.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.R
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun Notes(
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    navHostController: NavHostController,
    showGridOrLinearNotes: MutableState<Boolean>
) {

    val fontFamilyExtraLight = Font(R.font.lufgaextralight).toFontFamily()

//    viewModel.getNotesToShow()
//    val listOfNotes: SnapshotStateList<AddNote>? =
//        viewModel.getListOfNotesToShow.observeAsState().value

    var listOfNotesFromDB = remember { mutableStateListOf<Note>() }
    var listOfPinnedNotes = SnapshotStateList<Note>()
    viewModel.getAllNotes()
    activity.lifecycleScope.launch {
        listOfNotesFromDB = viewModel.listOFNotesFLow.first { true }.toMutableStateList()
        for (i in listOfNotesFromDB) {
            if (i.notePinned) {
                listOfPinnedNotes.add(i)
            }
        }
    }

//    viewModel.listOfNotesLiveData.observe(activity) {
//        for (note in listOfNotesFromDB) {
//            if (note.title.isEmpty() && note.content.isEmpty()) {
//                viewModel.deleteNoteById(note.id)
//            }
//        }
//    }


//    if (listOfNotesFromDB.isEmpty()) {
//        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
//            CircularProgressIndicator(
//                modifier = Modifier
//                    .padding(8.dp)
//                    .width(35.dp)
//                    .height(35.dp),
//                color = MaterialTheme.colors.onPrimary
//            )
//        }
//    }

    if (showGridOrLinearNotes.value) {
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
                        text = "Notes",
                        fontFamily = fontFamilyExtraLight, fontSize = 65.sp,
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
                SingleItemNoteList(note = note, navHostController = navHostController)
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
                items = listOfNotesFromDB ?: emptyList()
            ) { note ->
                SingleItemNoteList(note = note, navHostController)
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
                        text = "Notes",
                        fontFamily = fontFamilyExtraLight, fontSize = 65.sp,
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
                SingleItemNoteList(note = note, navHostController = navHostController)
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
                listOfNotesFromDB ?: emptyList()
            ) { note ->
                SingleItemNoteList(note = note, navHostController)
            }
        }
    }
}
//}
///}



