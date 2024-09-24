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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pzbdownloaders.scribble.common.domain.utils.CheckInternet
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.settings_feature.screen.domain.checkPasswordIfMatches
import com.pzbdownloaders.scribble.settings_feature.screen.domain.createPassword


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetPasswordAlertBox(
    password: MutableState<String>,
    confirmPassword: MutableState<String>,
    showPasswordDialog: MutableState<Boolean>,
    activity: MainActivity
) {

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
                    text = "Enter a strong password",
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.fontFamilyRegular,
                    color = MaterialTheme.colors.onPrimary
                )
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = {
                        Text(
                            text = "Enter Password",
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
                    text = "Confirm Password",
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.fontFamilyRegular,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(top = 5.dp)
                )
                OutlinedTextField(
                    value = confirmPassword.value,
                    onValueChange = { confirmPassword.value = it },
                    label = {
                        Text(
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
        }, confirmButton = {
            androidx.compose.material.Button(
                onClick = {

                    val result = checkPasswordIfMatches(password.value, confirmPassword.value)
                    if (result) {
                        val result = createPassword(password.value)
                        result.observe(activity) {
                            if (it == Constant.SUCCESS) {
                                Toast.makeText(
                                    context,
                                    "Password has been created",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else if (it == Constant.FAILURE) {
                                Toast.makeText(
                                    context,
                                    "Failed to create a password. Please try again",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            }
                            showPasswordDialog.value = false
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Passwords did not match",
                            Toast.LENGTH_SHORT
                        ).show()
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
                    text = "Set Lock",
                    fontFamily = FontFamily.fontFamilyRegular
                )
            }
        },
        dismissButton = {
            androidx.compose.material.OutlinedButton(
                onClick = { showPasswordDialog.value = false },
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