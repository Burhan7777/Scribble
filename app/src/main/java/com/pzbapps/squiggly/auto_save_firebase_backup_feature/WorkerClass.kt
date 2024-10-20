package com.pzbapps.squiggly.auto_save_firebase_backup_feature

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.pzbapps.squiggly.di.AppModule
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class BackupWorker(
    private val context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        // Add your logic to backup the Room database to Firebase Storage here
        backupDatabaseToFirebase(context)
        return Result.success()
    }

    private fun backupDatabaseToFirebase(context: Context) {
        // Implement your backup logic, similar to how you manually upload the database
        // This should handle getting the latest .db file and uploading it to Firebase Storage
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
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val date = Date()
                var finalDate = dateFormat.format(date)
                val storageRef = Firebase.storage.reference
                val childStorage =
                    storageRef.child("Notebook Database/$firebaseUserId/autosave-${finalDate}--${UUID.randomUUID()}.db")

                val inputStream = FileInputStream(fileOfNoteBackup)
                val uploadTask = childStorage.putStream(inputStream)

                uploadTask.addOnSuccessListener {
                    inputStream.close()

                }.addOnFailureListener { e ->

                    inputStream.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
        }
    }
}
