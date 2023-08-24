package com.pzbdownloaders.scribble.main_screen.presentation.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.R
import androidx.compose.foundation.lazy.items
import com.pzbdownloaders.scribble.add_note_screen.domain.AddNote
import com.pzbdownloaders.scribble.common.domain.utils.GetResult
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import kotlinx.coroutines.flow.asFlow

@Composable
fun Notes(
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    navHostController: NavHostController
) {

    val fontFamilyExtraLight = Font(R.font.lufgaextralight).toFontFamily()

    viewModel.getNotesToShow()
    val listOfNotes: SnapshotStateList<AddNote>? =
        viewModel.getListOfNotesToShow.observeAsState().value



    Log.i("list", listOfNotes.toString())
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

    LazyColumn() {
        items(listOfNotes?.toList() ?: emptyList()) { note ->
            SingleItemNoteList(note = note, navHostController)
        }
    }
}
///}



