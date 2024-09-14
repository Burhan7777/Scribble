package com.pzbdownloaders.scribble.settings_feature.screen.presentation.screens

import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.storage
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.Screens
import com.pzbdownloaders.scribble.settings_feature.screen.presentation.components.DatabaseBackupNameAlertBox
import com.pzbdownloaders.scribble.settings_feature.screen.presentation.components.DisplayBackupNamesAlertBox
import com.pzbdownloaders.scribble.settings_feature.screen.presentation.components.LoadingDialogBox
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@Composable
fun BackupAndRestoreScreen() {
    val context = LocalContext.current
    var backUpFileName = remember { mutableStateOf("") }
    var showBackUpFIleNameAlertBox = remember { mutableStateOf(false) }
    var listOfBackUpFiles = remember { MutableLiveData<ListResult>() }
    var showListOfBackupFilesDialogBox = remember { mutableStateOf(false) }
    var loadingDialogWhenBackingUp = remember { mutableStateOf(false) }
    var loadingDialogWhenRestoring = remember { mutableStateOf(false) }
    Column {
        Text(
            text = "Cloud Backup and Restore",
            fontFamily = FontFamily.fontFamilyBold,
            fontSize = 15.sp,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(10.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(10.dp)
                .border(
                    BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
                    MaterialTheme.shapes.medium.copy(
                        topStart = CornerSize(10.dp),
                        topEnd = CornerSize(10.dp),
                        bottomStart = CornerSize(10.dp),
                        bottomEnd = CornerSize(10.dp),
                    )
                )
                .clickable {
                    showBackUpFIleNameAlertBox.value = true
                },
            shape = androidx.compose.material3.MaterialTheme.shapes.medium.copy(
                topStart = CornerSize(10.dp),
                topEnd = CornerSize(10.dp),
                bottomStart = CornerSize(10.dp),
                bottomEnd = CornerSize(10.dp),
            ),
            elevation = CardDefaults.cardElevation(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                disabledContainerColor = MaterialTheme.colors.primary,
                disabledContentColor = MaterialTheme.colors.onPrimary
            )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Backup,
                    contentDescription = "Backup notes icon",
                    modifier = Modifier.padding(top = 12.dp, start = 10.dp)
                )
                Column() {
                    Text(
                        text = "Backup Notes",
                        modifier = Modifier.padding(top = 12.dp, start = 10.dp),
                        fontSize = 18.sp,
                        fontFamily = FontFamily.fontFamilyRegular,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "This uploads notes backup to  cloud.",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 12.sp,
                        fontFamily = FontFamily.fontFamilyRegular,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontStyle = FontStyle.Italic
                    )
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForwardIos,
                        contentDescription = "Arrow Forward",
                        tint = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .padding(top = 10.dp, end = 10.dp)
                            .align(Alignment.CenterEnd)
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(10.dp)
                .border(
                    BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
                    MaterialTheme.shapes.medium.copy(
                        topStart = CornerSize(10.dp),
                        topEnd = CornerSize(10.dp),
                        bottomStart = CornerSize(10.dp),
                        bottomEnd = CornerSize(10.dp),
                    )
                )
                .clickable {
                    var firebaseUserId = Firebase.auth.currentUser?.uid
                    var storageRef = Firebase.storage
                    var childStorage =
                        storageRef.reference.child("Notebook Database/$firebaseUserId/")
                    childStorage
                        .listAll()
                        .addOnSuccessListener {
                            if (it.items.isNotEmpty()) {
                                listOfBackUpFiles.value = it
                                showListOfBackupFilesDialogBox.value = true
                            } else {
                                Toast
                                    .makeText(
                                        context,
                                        "There is no backup stored",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        }
                },
            shape = androidx.compose.material3.MaterialTheme.shapes.medium.copy(
                topStart = CornerSize(10.dp),
                topEnd = CornerSize(10.dp),
                bottomStart = CornerSize(10.dp),
                bottomEnd = CornerSize(10.dp),
            ),
            elevation = CardDefaults.cardElevation(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                disabledContainerColor = MaterialTheme.colors.primary,
                disabledContentColor = MaterialTheme.colors.onPrimary
            )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Restore,
                    contentDescription = "Backup notes icon",
                    modifier = Modifier.padding(top = 12.dp, start = 10.dp)
                )
                Column() {
                    Text(
                        text = "Restore Notes",
                        modifier = Modifier.padding(top = 12.dp, start = 10.dp),
                        fontSize = 18.sp,
                        fontFamily = FontFamily.fontFamilyRegular,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "This restores the notes saved on cloud.",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 12.sp,
                        fontFamily = FontFamily.fontFamilyRegular,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontStyle = FontStyle.Italic
                    )
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForwardIos,
                        contentDescription = "Arrow Forward",
                        tint = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .padding(top = 10.dp, end = 10.dp)
                            .align(Alignment.CenterEnd)
                    )
                }
            }
        }
        if (showBackUpFIleNameAlertBox.value) {
            DatabaseBackupNameAlertBox(
                backUpFileName = backUpFileName,
                loadingDialogWhenBackingUp
            ) {
                showBackUpFIleNameAlertBox.value = false
            }
        }
        if (showListOfBackupFilesDialogBox.value) {
            DisplayBackupNamesAlertBox(
                listOfBackUpFiles = listOfBackUpFiles.value?.items!!,
                loadingDialogWhenRestoring
            ) {
                showListOfBackupFilesDialogBox.value = false
            }
        }
        if (loadingDialogWhenBackingUp.value) {
            LoadingDialogBox(text = mutableStateOf("Uploading to cloud"))
        }
        if (loadingDialogWhenRestoring.value) {
            LoadingDialogBox(text = mutableStateOf("Restoring backup"))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun preview() {
    BackupAndRestoreScreen()
}