package com.pzbdownloaders.scribble.common.presentation

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pzbdownloaders.scribble.auto_save_firebase_backup_feature.BackupWorker
import com.pzbdownloaders.scribble.common.domain.usecase.ShowFIrebaseMaintenanceAlertBox
import com.pzbdownloaders.scribble.common.domain.usecase.ShowFirebaseOtherPurposesAlertBox
import com.pzbdownloaders.scribble.common.domain.usecase.ShowFirebaseUpdateAlertBox
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.presentation.components.ShowFirebaseDialogBox
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import com.pzbdownloaders.scribble.ui.theme.ScribbleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

        var titleUpdate = mutableStateOf("")
        var bodyUpdate = mutableStateOf("")
        var showUpdate = mutableStateOf(false)
        var versionCodeUpdate = mutableStateOf("")
        var buttonNameUpdate = mutableStateOf("")

        var titleMaintenance = mutableStateOf("")
        var bodyMaintenance = mutableStateOf("")
        var showMaintenance = mutableStateOf(false)
        var versionCodeMaintenance = mutableStateOf("")
        var buttonNameMaintenance = mutableStateOf("")

        var titleOtherPurposes = mutableStateOf("")
        var bodyOtherPurposes = mutableStateOf("")
        var showOtherPurposes = mutableStateOf(false)
        var versionCodeOtherPurposes = mutableStateOf("")
        var buttonNameOtherPurposes = mutableStateOf("")


        val conMgr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        Log.i("network", netInfo.toString())
        deleteTrashNotes(viewModel, this)
        val prefs = getSharedPreferences(Constant.AUTO_SAVE_PREF, MODE_PRIVATE)
        val autoSave = prefs.getBoolean(Constant.AUTO_SAVE_KEY, false)

        var code = Int.MAX_VALUE
        try {
            var versionCode =
                packageManager.getPackageInfo(this.packageName, 0)
            code = versionCode.versionCode
        } catch (exception: PackageManager.NameNotFoundException) {

        }

        if (autoSave) {
            firebaseBackUp(this)
        }

        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }

        setContent {
            ScribbleTheme {

                var selectedIItem = remember {
                    mutableStateOf(0)
                }
                var selectedNote = remember {
                    mutableStateOf(0)
                }
                var showUpdateDialogBox = remember { mutableStateOf(false) }

                var showMaintenanceDialogBox = remember { mutableStateOf(false) }

                var showOtherPurposesDialogBox = remember { mutableStateOf(false) }


                ShowFirebaseUpdateAlertBox(
                    showUpdateDialogBox = showUpdateDialogBox,
                    show = showUpdate,
                    title = titleUpdate,
                    body = bodyUpdate,
                    versionCode = versionCodeUpdate,
                    buttonName = buttonNameUpdate,
                    code = code
                )

                ShowFIrebaseMaintenanceAlertBox(
                    showUpdateDialogBox = showMaintenanceDialogBox,
                    show = showMaintenance,
                    title = titleMaintenance,
                    body = bodyMaintenance,
                    versionCode = versionCodeMaintenance,
                    buttonName = buttonNameMaintenance,
                    code = code
                )

                ShowFirebaseOtherPurposesAlertBox(
                    showUpdateDialogBox = showOtherPurposesDialogBox,
                    show = showOtherPurposes,
                    title = titleOtherPurposes,
                    body = bodyOtherPurposes,
                    versionCode = versionCodeOtherPurposes,
                    buttonName = buttonNameOtherPurposes,
                    code = code
                )



                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.primary)
                ) {
                    if (showUpdateDialogBox.value) {
                        ShowFirebaseDialogBox(
                            title = titleUpdate.value,
                            body = bodyUpdate.value,
                            confirmButtonText = buttonNameUpdate.value,
                            onClick = {
                                var intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.pzbdownloaders.scribble")
                                )
                                startActivity(intent)
                            }
                        ) {
                            showUpdateDialogBox.value = false
                        }
                    }
                    if (showMaintenanceDialogBox.value) {
                        ShowFirebaseDialogBox(
                            title = titleMaintenance.value,
                            body = bodyMaintenance.value,
                            confirmButtonText = buttonNameMaintenance.value,
                            onClick = {}
                        ) {
                            showMaintenanceDialogBox.value = false
                        }
                    }
                    if (showOtherPurposesDialogBox.value) {
                        ShowFirebaseDialogBox(
                            title = titleOtherPurposes.value,
                            body = bodyOtherPurposes.value,
                            confirmButtonText = buttonNameOtherPurposes.value,
                            onClick = {}
                        ) {
                            showOtherPurposesDialogBox.value = false
                        }
                    }
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

