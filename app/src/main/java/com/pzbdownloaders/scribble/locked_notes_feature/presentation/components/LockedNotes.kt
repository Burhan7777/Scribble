package com.pzbdownloaders.scribble.locked_notes_feature.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.R
import com.pzbdownloaders.scribble.archive_notes_feature.presentation.components.SingleItemNoteList
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel

@Composable
fun LockedNotes(
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    navHostController: NavHostController
) {

    val fontFamilyExtraLight = Font(R.font.lufgaextralight).toFontFamily()

//    viewModel.getArchivedNotes()
//    val listOfNotes: SnapshotStateList<AddNote>? =
//        viewModel.getArchivedNotes.observeAsState().value

    viewModel.getAllNotes()
    var listOfNotes = viewModel.listOfNotes


    if (listOfNotes == null) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(8.dp)
                    .width(30.dp)
                    .height(30.dp),
                color = MaterialTheme.colors.onPrimary
            )
        }

    }
    Text(
        text = "My",
        fontFamily = fontFamilyExtraLight,
        fontSize = 65.sp,
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier.padding(horizontal = 10.dp)
    )
    Text(
        text = "Locked Notes",
        fontFamily = fontFamilyExtraLight, fontSize = 45.sp,
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier.padding(horizontal = 10.dp)
    )

    LazyColumn() {
        items(listOfNotes ?: emptyList()) { note ->
            SingleItemLockedNoteList(note = note, navHostController)
        }
    }
}