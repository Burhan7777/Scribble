package com.pzbdownloaders.scribble.common.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.pzbdownloaders.scribble.auto_save_firebase_backup_feature.BackupWorker
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import com.pzbdownloaders.scribble.ui.theme.ScribbleTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var viewModel: MainActivityViewModel
    lateinit var result: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.getAllNotebooks() // WE LOAD THE NOTEBOOKS IN THE START ONLY SO THAT TO SHOW THEM EVERYWHERE NEEDED.
        val sharedPreferences = getSharedPreferences("rememberUser", Context.MODE_PRIVATE)
        result = sharedPreferences.getString("LoggedInUser", "nothing")!!

        val conMgr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        Log.i("network", netInfo.toString())
        deleteTrashNotes(viewModel, this)
        val prefs = getSharedPreferences(Constant.AUTO_SAVE_PREF, MODE_PRIVATE)
        val autoSave = prefs.getBoolean(Constant.AUTO_SAVE_KEY, false)

        if (autoSave) {
            firebaseBackUp(this)
        }

        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }

        setContent {
            var showTrialEndedDialogBox = remember {
                mutableStateOf(false)
            }
            ScribbleTheme {
                // A surface container using the 'background' color from the theme
                var selectedIItem = remember {
                    mutableStateOf(0)
                }
                var selectedNote = remember {
                    mutableStateOf(0)
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.primary)
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        viewModel,
                        this@MainActivity,
                        result,
                        selectedIItem,
                        selectedNote
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ScribbleTheme {
    }
}


fun deleteTrashNotes(viewModel: MainActivityViewModel, activity: MainActivity) {
    viewModel.getAllNotes()
    viewModel.listOfNotesLiveData.observe(activity) {
        var notesInTrash = mutableStateOf(SnapshotStateList<Note>())
        // println(it.size)
        for (i in it) {
            if (i.deletedNote) {
                notesInTrash.value.add(i)
            }
        }

        println(notesInTrash.value.size)
        for (i in notesInTrash.value) {
            if ((System.currentTimeMillis() - i.timePutInTrash) > 1209600000) {
                viewModel.deleteNoteById(i.id)
                //  Toast.makeText(activity, "Trash cleared", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

fun firebaseBackUp(context: Context) {
    val backupRequest = PeriodicWorkRequestBuilder<BackupWorker>(72, TimeUnit.HOURS)
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) // Only run if connected to the internet
                .build()
        )
        .build()

    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork(
            "firebaseAutoSave",
            ExistingPeriodicWorkPolicy.KEEP,
            backupRequest
        )

}

