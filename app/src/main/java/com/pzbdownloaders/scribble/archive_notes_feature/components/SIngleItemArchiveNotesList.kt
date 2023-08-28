package com.pzbdownloaders.scribble.archive_notes_feature.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.R
import com.pzbdownloaders.scribble.add_note_feature.domain.model.AddNote
import com.pzbdownloaders.scribble.common.presentation.Constant
import com.pzbdownloaders.scribble.common.presentation.Screens

@Composable
fun SingleItemNoteList(note: AddNote, navHostController: NavHostController) {
    val fontFamilyExtraLight = Font(R.font.lufgaextralight).toFontFamily()
    //   val fontFamilyRegular = Font(R.font.lufgaregular).toFontFamily()
    val fontFamilyLight = Font(R.font.lufgalight).toFontFamily()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(10.dp)
            .border(
                BorderStroke(1.dp, androidx.compose.material.MaterialTheme.colors.onPrimary),
                androidx.compose.material.MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(10.dp),
                    topEnd = CornerSize(10.dp),
                    bottomStart = CornerSize(10.dp),
                    bottomEnd = CornerSize(10.dp),
                )
            )
            .clickable {
                navHostController.navigate(
                    Screens.EditNoteScreen.editNoteWithId(
                        note.noteId,
                        Constant.ARCHIVE
                    )
                )
                Log.i("title", note.title)
            },
        shape = MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(10.dp),
            topEnd = CornerSize(10.dp),
            bottomStart = CornerSize(10.dp),
            bottomEnd = CornerSize(10.dp),
        ),
        elevation = CardDefaults.cardElevation(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = androidx.compose.material.MaterialTheme.colors.primary,
            contentColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
            disabledContainerColor = androidx.compose.material.MaterialTheme.colors.primary,
            disabledContentColor = androidx.compose.material.MaterialTheme.colors.onPrimary
        )
    ) {
        Text(
            text = note.title,
            modifier = Modifier.padding(10.dp),
            fontSize = 35.sp,
            fontFamily = fontFamilyExtraLight
        )
        Text(
            text = note.content,
            modifier = Modifier.padding(10.dp),
            fontSize = 15.sp,
            overflow = TextOverflow.Ellipsis,
            fontFamily = fontFamilyLight
        )
    }
}
