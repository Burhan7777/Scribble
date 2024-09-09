package com.pzbdownloaders.scribble.edit_note_feature.presentation.components


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.edit_note_feature.domain.usecase.getPasswordFromFirebase
import com.pzbdownloaders.scribble.main_screen.domain.model.Note


@Composable
fun AlertDialogBoxEnterPassword(
    viewModel: MainActivityViewModel,
    id: Int,
    activity: MainActivity,
    navHostController: NavHostController,
    title: String,
    content: String,
    convertedMutableList: ArrayList<String>,
    listOfCheckboxes: ArrayList<Boolean>,
    listOfBulletPoints: ArrayList<String>,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val enteredPassword = remember { mutableStateOf("") }
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
        text = {
            Column {
                Text(
                    text = "Enter password to lock the note. ",
                    fontFamily = FontFamily.fontFamilyRegular,
                    color = MaterialTheme.colors.onPrimary
                )
                OutlinedTextField(
                    value = enteredPassword.value,
                    onValueChange = { enteredPassword.value = it },
                    label = {
                        androidx.compose.material3.Text(
                            text = "Confirm Password",
                            color = MaterialTheme.colors.onPrimary,
                            fontSize = 15.sp,
                            fontFamily = FontFamily.fontFamilyRegular,

                            )
                    },

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colors.onPrimary,
                        unfocusedTextColor = MaterialTheme.colors.onPrimary,
                        focusedTextColor = MaterialTheme.colors.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colors.onPrimary,
                        cursorColor = MaterialTheme.colors.onPrimary
                    ),
                    textStyle = TextStyle.Default.copy(
                        fontSize = 15.sp,
                        fontFamily = FontFamily.fontFamilyRegular
                    ),
                    shape = MaterialTheme.shapes.medium.copy(
                        topStart = CornerSize(15.dp),
                        topEnd = CornerSize(15.dp),
                        bottomEnd = CornerSize(15.dp),
                        bottomStart = CornerSize(15.dp),
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    println(convertedMutableList)
                    val result = getPasswordFromFirebase()
                    result.observe(activity) {
                        if (it == enteredPassword.value.trim()) {
                            var note = Note(
                                id = id,
                                title,
                                content = content,
                                locked = true,
                                archive = false,
                                listOfCheckedBoxes = listOfCheckboxes,
                                listOfCheckedNotes = convertedMutableList,
                               listOfBulletPointNotes =  listOfBulletPoints,
                                timeStamp = 123
                            )
                            viewModel.updateNote(note)
                            Toast.makeText(
                                activity,
                                "Note has been locked",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            navHostController.popBackStack()
                        } else if (it == Constant.FAILURE) {
                            Toast.makeText(
                                activity,
                                "Failed to get the password from server. Please try again",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (it != enteredPassword.value) {
                            Toast.makeText(activity, "Wrong Password", Toast.LENGTH_SHORT).show()
                        }
                    }
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
                Text(text = "Lock Note", fontFamily = FontFamily.fontFamilyRegular)
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