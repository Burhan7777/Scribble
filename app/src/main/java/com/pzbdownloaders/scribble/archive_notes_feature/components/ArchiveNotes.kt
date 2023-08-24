package com.pzbdownloaders.scribble.archive_notes_feature.components

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.R
import com.pzbdownloaders.scribble.add_note_feature.domain.AddNote
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel

@Composable
fun Notes(
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    navHostController: NavHostController
) {

    val fontFamilyExtraLight = Font(R.font.lufgaextralight).toFontFamily()

    viewModel.getArchivedNotes()
    val listOfNotes: SnapshotStateList<AddNote>? =
        viewModel.getArchivedNotes.observeAsState().value



    Log.i("list", listOfNotes.toString())
    Text(
        text = "My",
        fontFamily = fontFamilyExtraLight,
        fontSize = 65.sp,
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier.padding(horizontal = 10.dp)
    )
    Text(
        text = "Archive",
        fontFamily = fontFamilyExtraLight, fontSize = 65.sp,
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier.padding(horizontal = 10.dp)
    )

    LazyColumn() {
        items(listOfNotes?.toList() ?: emptyList()) { note ->
            SingleItemNoteList(note = note, navHostController)
        }
    }
}
///}



