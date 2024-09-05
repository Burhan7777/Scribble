package com.pzbdownloaders.scribble.settings_feature.screen.presentation.components


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.edit_note_feature.domain.usecase.getPasswordFromFirebase
import com.pzbdownloaders.scribble.settings_feature.screen.domain.changePassword
import com.pzbdownloaders.scribble.settings_feature.screen.domain.checkPasswordIfMatches
import com.pzbdownloaders.scribble.settings_feature.screen.domain.createPassword


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordAlertBox(
    changePasswordDialog: MutableState<Boolean>,
    activity: MainActivity
) {

    var oldPassword = remember{ mutableStateOf("") }
    var newPassword = remember{ mutableStateOf("") }

    val context = LocalContext.current
    androidx.compose.material3.AlertDialog(
        onDismissRequest = { /*TODO*/ },
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
                    text = "Enter old password",
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.fontFamilyRegular,
                    color = MaterialTheme.colors.onPrimary
                )
                OutlinedTextField(
                    value = oldPassword.value,
                    onValueChange = { oldPassword.value = it },
                    label = {
                        Text(
                            text = "Enter Old Password",
                            color = MaterialTheme.colors.onPrimary,
                            fontSize = 15.sp,
                            fontFamily = FontFamily.fontFamilyRegular
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
                Text(
                    text = "Enter new password",
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.fontFamilyRegular,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(top = 5.dp)
                )
                OutlinedTextField(
                    value = newPassword.value,
                    onValueChange = { newPassword.value = it },
                    label = {
                        Text(
                            text = "Enter New Password",
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
        }, confirmButton = {
            androidx.compose.material.Button(
                onClick = {
                    val result = getPasswordFromFirebase()
                    result.observe(activity) {
                        if (it == oldPassword.value) {
                            var result = changePassword(newPassword.value)
                            result.observe(activity) {
                                if (it) {
                                    Toast.makeText(
                                        activity,
                                        "Password changed successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    changePasswordDialog.value = false
                                } else {
                                    Toast.makeText(
                                        activity,
                                        "Failed to change password please try again",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                activity,
                                "Password entered is wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                    contentColor = androidx.compose.material.MaterialTheme.colors.primary
                ),
                shape = androidx.compose.material.MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(15.dp),
                    topEnd = CornerSize(15.dp),
                    bottomStart = CornerSize(15.dp),
                    bottomEnd = CornerSize(15.dp),
                )
            ) {
                androidx.compose.material.Text(
                    text = "Change Password",
                    fontFamily = FontFamily.fontFamilyRegular
                )
            }
        },
        dismissButton = {
            androidx.compose.material.OutlinedButton(
                onClick = { changePasswordDialog.value = false },
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