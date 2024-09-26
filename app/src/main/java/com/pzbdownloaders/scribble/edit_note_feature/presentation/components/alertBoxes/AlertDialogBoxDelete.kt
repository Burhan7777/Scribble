package com.pzbdownloaders.scribble.edit_note_feature.presentation.components.alertBoxes

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun AlertDialogBoxDelete(
    viewModel: MainActivityViewModel,
    id: Int,
    activity: MainActivity,
    navHostController: NavHostController,
    showDeletingNoteDialogBox: MutableState<Boolean>,
    //note: Note,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    var scope = rememberCoroutineScope()
    androidx.compose.material3.AlertDialog(
        onDismissRequest = {
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
                text = "Move to Trash Bin?",
                fontFamily = FontFamily.fontFamilyBold,
                fontSize = 20.sp,
                color = MaterialTheme.colors.onPrimary
            )
        },
        text = {
            Text(
                text = "Are you sure you want to move it to the trash bin ? ",
                fontFamily = FontFamily.fontFamilyRegular,
                color = MaterialTheme.colors.onPrimary
            )
        },
        confirmButton = {
            Button(
                onClick = {
                  ///  showDeletingNoteDialogBox.value = true

                    moveToTrash(
                        viewModel,
                        id,
                        scope,
                        navHostController,
                        context,
                        showDeletingNoteDialogBox,
                        onDismiss
                    )
                    //onDismiss()
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
                Text(text = "Move to Trash", fontFamily = FontFamily.fontFamilyRegular)
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

fun moveToTrash(
    viewModel: MainActivityViewModel,
    id: Int,
    scope: CoroutineScope,
    navHostController: NavHostController,
    context: Context,
    showDeletingDialogBox: MutableState<Boolean>,
    onDismiss: () -> Unit
) {
    scope.launch {
        viewModel.getNoteById(id)
        val note = viewModel.getNoteById.value
        // println("DELETE:$note")
        val note1 = note.copy(
            deletedNote = true,
            timePutInTrash = System.currentTimeMillis()
        )
        viewModel.updateNote(note1)
        //onDismiss()
        delay(200)
        viewModel.getNoteById(id)
        var note2 = viewModel.getNoteById.value
        if (!note2.deletedNote) {
            println("DELETED TRIGGERED")
            moveToTrash(
                viewModel,
                id,
                scope,
                navHostController,
                context,
                showDeletingDialogBox,
                onDismiss
            )
        } else {
            // println("DELETE:$note1")
       //     showDeletingDialogBox.value = false
            delay(200)
            navHostController.navigateUp()
            delay(300)
            Toast.makeText(context, "Note moved to trash", Toast.LENGTH_SHORT).show()
        }
    }
}