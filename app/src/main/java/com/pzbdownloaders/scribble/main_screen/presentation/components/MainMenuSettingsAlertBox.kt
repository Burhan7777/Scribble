package com.pzbdownloaders.scribble.main_screen.presentation.components
//
//import android.widget.Toast
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.CornerSize
//import androidx.compose.material.Button
//import androidx.compose.material.ButtonDefaults
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.OutlinedButton
//import androidx.compose.material.Text
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.pzbdownloaders.scribble.common.presentation.FontFamily
//import com.pzbdownloaders.scribble.common.presentation.Screens
//import com.pzbdownloaders.scribble.edit_note_feature.domain.usecase.getPasswordFromFirebase
//
//@Composable
//fun MainMenuSettingsAlertBox() {
//    androidx.compose.material3.AlertDialog(
//        onDismissRequest = {
//            onDismiss()
//        },
//        shape = MaterialTheme.shapes.medium.copy(
//            topStart = CornerSize(15.dp),
//            topEnd = CornerSize(15.dp),
//            bottomStart = CornerSize(15.dp),
//            bottomEnd = CornerSize(15.dp),
//        ),
//        containerColor = MaterialTheme.colors.primaryVariant,
//        title = {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp)
//                    .clip(CircleShape)
//                    .border(
//                        1.dp,
//                        MaterialTheme.colors.onPrimary,
//                        shape = CircleShape
//                    ),
//                shape = MaterialTheme.shapes.medium.copy(
//                    topStart = CornerSize(15.dp),
//                    topEnd = CornerSize(15.dp),
//                    bottomStart = CornerSize(15.dp),
//                    bottomEnd = CornerSize(15.dp),
//                ),
//                colors = CardDefaults.cardColors(
//                    containerColor = MaterialTheme.colors.primary,
//                    contentColor = MaterialTheme.colors.onPrimary
//                )
//            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight()
//                        .padding(start = 20.dp),
//                ) {
//                    androidx.compose.material3.Text(
//                        text = "",
//                        fontSize = 15.sp,
//                        fontFamily = FontFamily.fontFamilyRegular,
//                        color = MaterialTheme.colors.onPrimary,
//                        modifier = Modifier.align(Alignment.CenterStart)
//                    )
//                }
//            }
//        },
//        confirmButton = {
//            Button(
//                onClick = {
//
//                },
//                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = MaterialTheme.colors.onPrimary,
//                    contentColor = MaterialTheme.colors.primary
//                ),
//                shape = MaterialTheme.shapes.medium.copy(
//                    topStart = CornerSize(15.dp),
//                    topEnd = CornerSize(15.dp),
//                    bottomStart = CornerSize(15.dp),
//                    bottomEnd = CornerSize(15.dp),
//                )
//            ) {
//                Text(text = "Save Settings", fontFamily = FontFamily.fontFamilyRegular)
//            }
//        },
//        dismissButton = {
//            OutlinedButton(
//                onClick = { onDismiss() },
//                shape = MaterialTheme.shapes.medium.copy(
//                    topStart = CornerSize(15.dp),
//                    topEnd = CornerSize(15.dp),
//                    bottomStart = CornerSize(15.dp),
//                    bottomEnd = CornerSize(15.dp),
//                ),
//                border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
//                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = MaterialTheme.colors.primaryVariant,
//                    contentColor = MaterialTheme.colors.onPrimary
//                ),
//            ) {
//                Text(text = "Cancel", fontFamily = FontFamily.fontFamilyRegular)
//            }
//        }
//    )
//}