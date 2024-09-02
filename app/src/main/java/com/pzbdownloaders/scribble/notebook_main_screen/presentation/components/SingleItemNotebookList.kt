package com.pzbdownloaders.scribble.notebook_main_screen.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.Screens
import com.pzbdownloaders.scribble.main_screen.domain.model.Note

@Composable
fun SingleItemNotebookList(note: Note, navHostController: NavHostController) {

    //  if(note.archive) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(10.dp)
            .border(
                BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
                MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(10.dp),
                    topEnd = CornerSize(10.dp),
                    bottomStart = CornerSize(10.dp),
                    bottomEnd = CornerSize(10.dp),
                )
            )
            .clickable {
                navHostController.navigate(
                    Screens.EditNoteScreen.editNoteWithId(
                        note.id,
                        Constant.ARCHIVE
                    )
                )
            },
        shape = androidx.compose.material3.MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(10.dp),
            topEnd = CornerSize(10.dp),
            bottomStart = CornerSize(10.dp),
            bottomEnd = CornerSize(10.dp),
        ),
        elevation = CardDefaults.cardElevation(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            disabledContainerColor = MaterialTheme.colors.primary,
            disabledContentColor = MaterialTheme.colors.onPrimary
        )
    ) {
        Text(
            text = note.title,
            modifier = Modifier.padding(10.dp),
            fontSize = 25.sp,
            fontFamily = FontFamily.fontFamilyBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = note.content,
            modifier = Modifier.padding(10.dp),
            fontSize = 15.sp,
            overflow = TextOverflow.Ellipsis,
            fontFamily = FontFamily.fontFamilyLight
        )
    }
    // }
}