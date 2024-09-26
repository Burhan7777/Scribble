package com.pzbdownloaders.scribble.trash_bin_feature.presentation.components

import android.provider.ContactsContract.CommonDataKinds.Note
import com.pzbdownloaders.scribble.main_screen.presentation.components.SingleItemNoteList


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.R
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel

@Composable
fun TrashNotes(
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    navHostController: NavHostController,
    listOfNotesFromDb: MutableState<SnapshotStateList<com.pzbdownloaders.scribble.main_screen.domain.model.Note>>
) {

    val fontFamilyExtraLight = Font(R.font.lufgaextralight).toFontFamily()

//    viewModel.getNotesToShow()
//    val listOfNotes: SnapshotStateList<AddNote>? =
//        viewModel.getListOfNotesToShow.observeAsState().value

//
//    if (listOfNotesFromDB..va.isEmpty()) {
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
                text = "Trash",
                fontFamily = fontFamilyExtraLight, fontSize = 65.sp,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
        item {
            Card(
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(15.dp),
                    topEnd = CornerSize(15.dp),
                    bottomStart = CornerSize(15.dp),
                    bottomEnd = CornerSize(15.dp),
                ),
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "Trash is emptied every 14 days",
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.fontFamilyBold
                )
            }
        }
        items(
            listOfNotesFromDb.value ?: emptyList()
        ) { note ->
            SingleItemTrashNoteList(note = note, navHostController)
        }
    }
}
///}



