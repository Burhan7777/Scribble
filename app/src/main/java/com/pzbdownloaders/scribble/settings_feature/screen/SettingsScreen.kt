package com.pzbdownloaders.scribble.settings_feature.screen

import android.graphics.fonts.FontStyle
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.data.Model.NoteBook
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.Screens

@Composable
fun SettingsScreen(navHostController: NavHostController) {
    var showPasswordDialog = remember { mutableStateOf(false) }
    var password = remember { mutableStateOf("") }
    var confirmPassword = remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIos,
                    contentDescription = "Back to main screen",
                    tint = androidx.compose.material.MaterialTheme.colors.onPrimary
                )
            }
            Text(
                text = "Settings",
                fontFamily = FontFamily.fontFamilyBold,
                fontSize = 35.sp,
                modifier = Modifier.padding(start=5.dp),
                color = androidx.compose.material.MaterialTheme.colors.onPrimary
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
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
                    showPasswordDialog.value = true

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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Lock icon",
                    modifier = Modifier.padding(top = 12.dp, start = 10.dp)
                )
                Text(
                    text = "Password Protect Notes",
                    modifier = Modifier.padding(top = 12.dp, start = 10.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.fontFamilyRegular,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Box(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForwardIos,
                        contentDescription = "Arrow Forward",
                        tint = androidx.compose.material.MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .padding(top = 10.dp, end = 10.dp)
                            .align(Alignment.CenterEnd)
                    )
                }
            }
        }
        if (showPasswordDialog.value) {
            SetPasswordAlertBox(
                password = password,
                confirmPassword = confirmPassword,
                showPasswordDialog = showPasswordDialog
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetPasswordAlertBox(
    password: MutableState<String>,
    confirmPassword: MutableState<String>,
    showPasswordDialog: MutableState<Boolean>
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
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.fontFamilyRegular,
                    color = androidx.compose.material.MaterialTheme.colors.onPrimary
                )
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = {
                        Text(
                            text = "Enter Password",
                            color = androidx.compose.material.MaterialTheme.colors.onPrimary,
                            fontSize = 15.sp,
                            fontFamily = FontFamily.fontFamilyRegular
                        )
                    },

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                        unfocusedTextColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                        focusedTextColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                        unfocusedBorderColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                        cursorColor = androidx.compose.material.MaterialTheme.colors.onPrimary
                    ),
                    textStyle = TextStyle.Default.copy(
                        fontSize = 15.sp,
                        fontFamily = FontFamily.fontFamilyRegular
                    ),
                    shape = androidx.compose.material.MaterialTheme.shapes.medium.copy(
                        topStart = CornerSize(15.dp),
                        topEnd = CornerSize(15.dp),
                        bottomEnd = CornerSize(15.dp),
                        bottomStart = CornerSize(15.dp),
                    )
                )
                Text(
                    text = "Confirm Password",
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.fontFamilyRegular,
                    color = androidx.compose.material.MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(top = 5.dp)
                )
                OutlinedTextField(
                    value = confirmPassword.value,
                    onValueChange = { confirmPassword.value = it },
                    label = {
                        Text(
                            text = "Confirm Password",
                            color = androidx.compose.material.MaterialTheme.colors.onPrimary,
                            fontSize = 15.sp,
                            fontFamily = FontFamily.fontFamilyRegular,

                            )
                    },

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                        unfocusedTextColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                        focusedTextColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                        unfocusedBorderColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                        cursorColor = androidx.compose.material.MaterialTheme.colors.onPrimary
                    ),
                    textStyle = TextStyle.Default.copy(
                        fontSize = 15.sp,
                        fontFamily = FontFamily.fontFamilyRegular
                    ),
                    shape = androidx.compose.material.MaterialTheme.shapes.medium.copy(
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
                    if (password.value.trim() == confirmPassword.value.trim()) {
                        Toast.makeText(
                            context,
                            "Password Saved",
                            Toast.LENGTH_SHORT
                        ).show()
                        showPasswordDialog.value = false
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
                    androidx.compose.material.MaterialTheme.colors.onPrimary
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