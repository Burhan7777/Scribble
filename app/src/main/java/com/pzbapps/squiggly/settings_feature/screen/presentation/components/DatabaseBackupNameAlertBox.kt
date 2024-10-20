package com.pzbapps.squiggly.settings_feature.screen.presentation.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.ktx.storage
import com.pzbapps.squiggly.common.presentation.FontFamily
import com.pzbapps.squiggly.di.AppModule
import java.io.File
import java.io.FileInputStream

@Composable
fun DatabaseBackupNameAlertBox(
    backUpFileName: MutableState<String>,
    loadingDialog: MutableState<Boolean>,
    onDismiss: () -> Unit,

    ) {
    var context = LocalContext.current
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
                    text = "Enter the name of backup file",
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.fontFamilyRegular,
                    color = MaterialTheme.colors.onPrimary
                )
                OutlinedTextField(
                    value = backUpFileName.value,
                    onValueChange = { backUpFileName.value = it },
                    label = {
                        Text(
                            text = "Enter file name",
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
            }
        }, confirmButton = {
            androidx.compose.material.Button(
                onClick = {
                    loadingDialog.value = true
                    val firebaseUserId = Firebase.auth.currentUser?.uid
                    val currentDBPath = context.getDatabasePath("notes").absolutePath
                    val fileOfNoteBackup = File(currentDBPath)

                    if (fileOfNoteBackup.exists()) {
                        try {
                            // Create a temporary file to copy the database content
//                            val tempBackupFile =
//                                File.createTempFile("temp_backup", ".db", context.cacheDir)
//                            fileOfNoteBackup.copyTo(tempBackupFile, overwrite = true)

                            // Ensure all database changes are flushed
//                            val db = Room.databaseBuilder(
//                                context,
//                                NoteDatabase::class.java, "notes"
//                            ).allowMainThreadQueries() // Temporarily allow main thread queries
//                                .build()
                            var db1 = AppModule().createDataBase(context)
                            db1.close()
//                            db1.getDao()
//                                .checkpoint(SimpleSQLiteQuery("pragma wal_checkpoint(full)"))


                            // Check if the tempBackupFile contains data
                            val storageRef = Firebase.storage.reference
                            val childStorage =
                                storageRef.child("Notebook Database/$firebaseUserId/${backUpFileName.value}.db")

                            val inputStream = FileInputStream(fileOfNoteBackup)
                            val uploadTask = childStorage.putStream(inputStream)

                            uploadTask.addOnSuccessListener {
                                loadingDialog.value = false
                                Toast.makeText(
                                    context,
                                    "Backup successfully created",
                                    Toast.LENGTH_SHORT
                                ).show()
                                inputStream.close()

                            }.addOnFailureListener { e ->
                                if (e is StorageException) {
                                    println("Error Code: ${e.errorCode}")
                                    println("HTTP Result Code: ${e.httpResultCode}")
                                    println("Inner Exception: ${e.cause}")
                                }
                                inputStream.close()
                                loadingDialog.value = false
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        Toast.makeText(context, "Database doesn't exist", Toast.LENGTH_SHORT).show()
                    }

                    onDismiss()
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
                    text = "Create Backup",
                    fontFamily = FontFamily.fontFamilyRegular
                )
            }
        },
        dismissButton = {
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