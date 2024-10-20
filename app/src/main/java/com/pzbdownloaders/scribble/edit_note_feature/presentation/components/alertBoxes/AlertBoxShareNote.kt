package com.pzbdownloaders.scribble.edit_note_feature.presentation.components.alertBoxes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.convertMutableStateIntoString
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.shareFiles.bulletpoints.exportBulletPointsToDocx
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.shareFiles.bulletpoints.shareBulletPointsAsText
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.shareFiles.checkboxes.exportToDocxCheckBoxes
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.shareFiles.checkboxes.exportToTextCheckBoxes
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.shareFiles.notes.exportAndShareDocx
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.shareFiles.notes.sharePlainText
import exportAndShareBulletPointsPdf
import exportAndSharePDF
import exportAndSharePdfCheckBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertBoxShareNote(
    title: String,
    content: String,
    checkboxes: MutableState<ArrayList<Boolean>>,
    checkboxesText: SnapshotStateList<MutableState<String>>,
    converted: ArrayList<String>,
    listOfBulletPoints: SnapshotStateList<MutableState<String>>,
    convertedBulletPoints: ArrayList<String>,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    androidx.compose.material3.AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = androidx.compose.material.MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(15.dp),
            topEnd = CornerSize(15.dp),
            bottomStart = CornerSize(15.dp),
            bottomEnd = CornerSize(15.dp),
        ),
        containerColor = androidx.compose.material.MaterialTheme.colors.primaryVariant,
        title = {
            Column {
                Text(
                    text = "Share file as...",
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.fontFamilyRegular,
                    color = MaterialTheme.colors.onPrimary
                )
                LazyColumn() {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .height(50.dp)
                                .clickable {
                                    if (checkboxes.value.size == 0 && listOfBulletPoints.size == 0) {
                                        exportAndSharePDF(context, title, content)
                                    } else if (listOfBulletPoints.size == 0) {
                                        convertMutableStateIntoString(checkboxesText, converted)
                                        exportAndSharePdfCheckBox(
                                            context,
                                            title,
                                            checkboxes.value,
                                            converted
                                        )
                                    } else {
                                        convertMutableStateIntoString(
                                            listOfBulletPoints,
                                            convertedBulletPoints
                                        )
                                        exportAndShareBulletPointsPdf(
                                            context,
                                            title,
                                            convertedBulletPoints
                                        )
                                    }
                                },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colors.primary,
                                contentColor = MaterialTheme.colors.onPrimary
                            ),

                            ) {
                            Text(
                                "Share as Pdf",
                                color = MaterialTheme.colors.onPrimary,
                                fontFamily = FontFamily.fontFamilyRegular,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                            )
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .height(50.dp)
                                .clickable {
                                    if (checkboxes.value.size == 0 && listOfBulletPoints.size == 0) {
                                        exportAndShareDocx(context, title, content)
                                    } else if (listOfBulletPoints.size == 0) {
                                        convertMutableStateIntoString(checkboxesText, converted)
                                        exportToDocxCheckBoxes(
                                            context,
                                            title,
                                            checkboxes.value,
                                            converted
                                        )
                                    } else {
                                        convertMutableStateIntoString(
                                            listOfBulletPoints,
                                            convertedBulletPoints
                                        )
                                        exportBulletPointsToDocx(
                                            context,
                                            title,
                                            convertedBulletPoints
                                        )
                                    }
                                },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colors.primary,
                                contentColor = MaterialTheme.colors.onPrimary
                            )
                        ) {
                            Text(
                                "Share as .docx",
                                color = MaterialTheme.colors.onPrimary,
                                fontFamily = FontFamily.fontFamilyRegular,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                            )
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .height(50.dp)
                                .clickable {
                                    if (checkboxes.value.size == 0 && listOfBulletPoints.size == 0) {
                                        sharePlainText(context, title, content)
                                    } else if (listOfBulletPoints.size == 0 ) {
                                        convertMutableStateIntoString(checkboxesText, converted)
                                        exportToTextCheckBoxes(
                                            context,
                                            title,
                                            checkboxes.value,
                                            converted
                                        )
                                    } else {
                                        convertMutableStateIntoString(
                                            listOfBulletPoints,
                                            convertedBulletPoints
                                        )
                                        shareBulletPointsAsText(
                                            context,
                                            title,
                                            convertedBulletPoints
                                        )
                                    }
                                },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colors.primary,
                                contentColor = MaterialTheme.colors.onPrimary
                            )
                        ) {
                            Text(
                                "Share as text",
                                color = MaterialTheme.colors.onPrimary,
                                fontFamily = FontFamily.fontFamilyRegular,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }, confirmButton = {
        }, dismissButton = {
            androidx.compose.material.OutlinedButton(
                onClick = { onDismiss() },
                shape = androidx.compose.material.MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(15.dp),
                    topEnd = CornerSize(15.dp),
                    bottomStart = CornerSize(15.dp),
                    bottomEnd = CornerSize(15.dp),
                ),
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colors.onPrimary
                ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = androidx.compose.material.MaterialTheme.colors.primaryVariant,
                    contentColor = androidx.compose.material.MaterialTheme.colors.onPrimary
                ),
            ) {
                androidx.compose.material.Text(
                    text = "Cancel",
                    fontFamily = FontFamily.fontFamilyRegular
                )
            }
        })
}