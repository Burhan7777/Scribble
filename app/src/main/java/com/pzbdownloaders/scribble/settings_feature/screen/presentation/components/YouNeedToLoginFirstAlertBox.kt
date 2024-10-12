package com.pzbdownloaders.scribble.settings_feature.screen.presentation.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.chaquo.python.Python
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun YouNeedToLoginFirst(navHostController: NavHostController, onDismiss: () -> Unit) {
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
            Text(
                text = "In order to access this feature you need to log in first. By logging in, app can save " +
                        "your passwords and backups on cloud. It will take few seconds of your time and provide more security to you.",
                fontSize = 15.sp,
                fontFamily = FontFamily.fontFamilyRegular,
                color = MaterialTheme.colors.onPrimary
            )
        }, confirmButton = {
            androidx.compose.material.Button(
                onClick = {
                    onDismiss()
                    navHostController.navigate(Screens.LoginScreen.route)
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
                    text = "Log In",
                    fontFamily = FontFamily.fontFamilyRegular
                )
            }
        },
        dismissButton = {
            androidx.compose.material.OutlinedButton(
                onClick = {
                    onDismiss()
                },
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