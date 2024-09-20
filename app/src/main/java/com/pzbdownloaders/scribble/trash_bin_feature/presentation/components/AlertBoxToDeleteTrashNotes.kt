package com.pzbdownloaders.scribble.trash_bin_feature.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.main_screen.domain.model.Note

@Composable
fun AlertBoxToDeleteTrashNotes(
    note: Note,
    viewModel: MainActivityViewModel,
    navHostController: NavHostController,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    androidx.compose.material3.AlertDialog(onDismissRequest = {
        onDismiss()
    },
        shape = MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(15.dp),
            topEnd = CornerSize(15.dp),
            bottomStart = CornerSize(15.dp),
            bottomEnd = CornerSize(15.dp),
        ),
        containerColor = MaterialTheme.colors.primaryVariant,
        /*      icon = {
                     Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
              }*/

        title = {
            Text(
                text = "Delete Note?",
                fontFamily = FontFamily.fontFamilyBold,
                fontSize = 20.sp,
                color = MaterialTheme.colors.onPrimary
            )
        },
        text = {
            Text(
                text = "Are you sure you want to delete this note permanently  ? ",
                fontFamily = FontFamily.fontFamilyRegular,
                color = MaterialTheme.colors.onPrimary
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.deleteNoteById(note.id)
                    onDismiss()
                    navHostController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.onPrimary,
                    contentColor = MaterialTheme.colors.primary
                ),
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(15.dp),
                    topEnd = CornerSize(15.dp),
                    bottomStart = CornerSize(15.dp),
                    bottomEnd = CornerSize(15.dp),
                )
            ) {
                Text(text = "Delete Permanently", fontFamily = FontFamily.fontFamilyRegular)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = { onDismiss() },
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(15.dp),
                    topEnd = CornerSize(15.dp),
                    bottomStart = CornerSize(15.dp),
                    bottomEnd = CornerSize(15.dp),
                ),
                border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    contentColor = MaterialTheme.colors.onPrimary
                ),
            ) {
                Text(text = "Cancel", fontFamily = FontFamily.fontFamilyRegular)
            }
        }
    )
}