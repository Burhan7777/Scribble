package com.pzbdownloaders.scribble.archive_notes_feature.presentation.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.add_note_feature.domain.model.AddNote
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.Screens
import com.pzbdownloaders.scribble.main_screen.domain.model.Note

@Composable
fun SingleItemArchiveNoteList(note: Note, navHostController: NavHostController,activity:MainActivity) {


    if (note.archive && note.listOfCheckedNotes.size == 0) {
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
                            note.id,
                            Constant.ARCHIVE
                        )
                    )
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
                fontSize = 25.sp,
                fontFamily = com.pzbdownloaders.scribble.common.presentation.FontFamily.fontFamilyBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = note.content,
                modifier = Modifier.padding(10.dp),
                fontSize = 15.sp,
                overflow = TextOverflow.Ellipsis,
                fontFamily = com.pzbdownloaders.scribble.common.presentation.FontFamily.fontFamilyLight
            )
        }
    } else if (note.listOfCheckedNotes.size > 0 && note.archive) {

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
                            note.id,
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
            println(note.listOfCheckedNotes.size)
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = note.title,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 25.sp,
                    fontFamily = FontFamily.fontFamilyBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = ((-22).dp),
                        alignment = Alignment.CenterVertically
                    )
                ) {

                    var listSize = note.listOfCheckedNotes.size
                    if (listSize >= 3) {
                        for (i in 0..2) {

                            Row(
                                horizontalArrangement = Arrangement.spacedBy((-5).dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = note.listOfCheckedBoxes[i],
                                    {
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                                        checkmarkColor = androidx.compose.material.MaterialTheme.colors.onSecondary,
                                        uncheckedColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                                        disabledCheckedColor = MaterialTheme.colorScheme.onPrimary,
                                    ),
                                    enabled = false,
                                )
                                Text(
                                    text = note.listOfCheckedNotes[i],
                                    fontFamily = FontFamily.fontFamilyRegular,
                                    fontSize = 15.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                )
                            }
                        }


                    } else {
                        for (i in note.listOfCheckedNotes.indices) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy((-5).dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = note.listOfCheckedBoxes[i],
                                    {
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                                        checkmarkColor = androidx.compose.material.MaterialTheme.colors.onSecondary,
                                        uncheckedColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                                        disabledCheckedColor = MaterialTheme.colorScheme.onPrimary,
                                    ),
                                    enabled = false,
                                )
                                Text(
                                    text = note.listOfCheckedNotes[i],
                                    fontFamily = FontFamily.fontFamilyRegular,
                                    fontSize = 15.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
