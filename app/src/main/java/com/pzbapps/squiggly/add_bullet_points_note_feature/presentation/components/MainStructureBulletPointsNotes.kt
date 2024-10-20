package com.pzbapps.squiggly.add_bullet_points_note_feature.presentation.components

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.pzbapps.squiggly.add_note_feature.presentation.components.DiscardNoteAlertBox
import com.pzbapps.squiggly.common.presentation.MainActivity
import com.pzbapps.squiggly.common.presentation.MainActivityViewModel
import com.pzbapps.squiggly.common.presentation.components.AlertDialogBoxTrialEnded
import com.pzbapps.squiggly.main_screen.domain.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainStructureBulletPointsNotes(
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    notebookState: MutableState<String>,
    title: MutableState<String>,
    activity: MainActivity
) {
    var context = LocalContext.current
    val scope = rememberCoroutineScope()

    var mutableListOfBulletPointsNotes = RememberSaveableSnapshotStateList()

    var mutableListConverted = rememberSaveable {
        ArrayList<String>()
    }

    var showTrialEndedDialogBox = remember {
        mutableStateOf(
            false
        )
    }

    var showDiscardNoteAlertBox = remember { mutableStateOf(false) }

    var generatedNoteId = rememberSaveable { mutableStateOf<Long>(0) }

    var count = rememberSaveable {
        mutableStateOf(0)

    }

    LaunchedEffect(key1 = true) {
        if (mutableListOfBulletPointsNotes.isEmpty()) {
            mutableListOfBulletPointsNotes.add(mutableStateOf(""))
        }

    }


    if (showTrialEndedDialogBox.value) {
        AlertDialogBoxTrialEnded {
            showTrialEndedDialogBox.value = false
        }
    }

    if (generatedNoteId.value.toInt() == 0) {
        LaunchedEffect(key1 = true) {
            var note = Note(
                title = title.value,
                timeModified = System.currentTimeMillis(),
                notebook = notebookState.value,
                timeStamp = System.currentTimeMillis(),
//            listOfCheckedNotes = mutableListConverted,
//            listOfCheckedBoxes = mutableListOfCheckBoxes,

            )
            viewModel.insertNote(note)

        }
    }
    viewModel.generatedNoteId.observe(activity) {
        generatedNoteId.value = it
        // Schedule a task to run every 10 seconds
    }

    //   println("LIST:${listOfCheckedNotes.size}")
    LaunchedEffect(key1 = count.value) {
        convertMutableStateIntoString(
            mutableListOfBulletPointsNotes,
            mutableListConverted
        )
        delay(500)

//        for (i in listOfCheckedNotes) {
//            println("TEXT1:${i.value}")
//        }
//
//        for (i in mutableListConverted) {
//            println("TEXT2:${i}")
//        }

        mutableListConverted.removeAll { it == "" }

        var note1 = Note(
            id = generatedNoteId.value.toInt(),
            title = title.value,
            timeModified = System.currentTimeMillis(),
            timeStamp = System.currentTimeMillis(),
            notebook = notebookState.value,
            listOfBulletPointNotes = mutableListConverted
        )
        viewModel.updateNote(note1)
    }

    var remember = rememberCoroutineScope()
    BackHandler {
        // keyboardController?.hide()
        remember.launch(Dispatchers.Main) {
            count.value++
            navController.popBackStack()
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                convertMutableStateIntoString(
                    mutableListOfBulletPointsNotes,
                    mutableListConverted
                )
                // Trigger autosave when app goes to background (onStop)
                activity.lifecycleScope.launch {
                    mutableListConverted.removeAll { it == "" }

                    var note1 = Note(
                        id = generatedNoteId.value.toInt(),
                        title = title.value,
                        timeModified = System.currentTimeMillis(),
                        timeStamp = System.currentTimeMillis(),
                        notebook = notebookState.value,
                        listOfBulletPointNotes = mutableListConverted
                    )
                    viewModel.updateNote(note1)
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        // Cleanup the observer when the Composable is disposed
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

//    LaunchedEffect(key1 = mutableListOfCheckboxTexts.size > 0) {
//        mutableListOfCheckBoxes.add(false)
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colors.primary
                ),
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = {
                        convertMutableStateIntoString(
                            mutableListOfBulletPointsNotes,
                            mutableListConverted
                        )
                        if (title.value.isNotEmpty() || (mutableListConverted.size != 1 || mutableListConverted[0].isNotEmpty())) {
                            val note = Note(
                                id = generatedNoteId.value.toInt(),
                                title = title.value,
                                notebook = notebookState.value,
                                listOfBulletPointNotes = mutableListConverted,
                                timeStamp = System.currentTimeMillis(),
                                timeModified = System.currentTimeMillis()
                            )
                            viewModel.updateNote(note)
                            Toast.makeText(activity, "Note has been saved", Toast.LENGTH_SHORT)
                                .show()
                            scope.launch {
                                delay(200)
                                navController.navigateUp()
                            }
                        } else {
                            viewModel.deleteNoteById(generatedNoteId.value.toInt())
                            Toast.makeText(context, "Empty note discarded", Toast.LENGTH_SHORT)
                                .show()
                            navController.navigateUp()
                        }

                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Undo",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        showDiscardNoteAlertBox.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Discard Note",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                    IconButton(onClick = {
                        convertMutableStateIntoString(
                            mutableListOfBulletPointsNotes,
                            mutableListConverted
                        )
                        if (title.value.isNotEmpty() || (mutableListConverted.size != 1 || mutableListConverted[0].isNotEmpty())) {
                            val note = Note(
                                id = generatedNoteId.value.toInt(),
                                title = title.value,
                                notebook = notebookState.value,
                                listOfBulletPointNotes = mutableListConverted,
                                timeStamp = System.currentTimeMillis(),
                                timeModified = System.currentTimeMillis()
                            )
                            viewModel.updateNote(note)
                            Toast.makeText(activity, "Note has been saved", Toast.LENGTH_SHORT)
                                .show()
                            scope.launch {
                                delay(200)
                                navController.navigateUp()
                            }
                        } else {
                            viewModel.deleteNoteById(generatedNoteId.value.toInt())
                            Toast.makeText(context, "Empty note discarded", Toast.LENGTH_SHORT)
                                .show()
                            navController.navigateUp()
                        }


                    }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Save",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            )
        },

        // ADD AGAIN WHEN FEATURES WOULD BE ADDED TO BottomAppBar

        /*    bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {

                }
            }*/
    ) {
        Column(modifier = Modifier.padding(it)) {
            if (showDiscardNoteAlertBox.value) {
                DiscardNoteAlertBox(
                    viewModel = viewModel,
                    navHostController = navController,
                    activity = activity,
                    id = generatedNoteId.value.toInt()
                ) {
                    showDiscardNoteAlertBox.value = false
                }
            }
            BulletPointNote(
                viewModel,
                navController,
                notebookState,
                title,
                mutableListOfBulletPointsNotes,
                count,
                mutableListConverted
            )
        }
    }
}

fun convertMutableStateIntoString(
    mutableList: SnapshotStateList<MutableState<String>>,
    mutableListConverted: ArrayList<String>
) {
    for (i in mutableList) {
        if (!mutableListConverted.contains(i.value)) {
            mutableListConverted.add(i.value)
        }
    }
}

@Composable
fun RememberSaveableSnapshotStateList(): SnapshotStateList<MutableState<String>> {
    // Create a custom saver for SnapshotStateList<Mutable<String>>
    val listSaver = Saver<SnapshotStateList<MutableState<String>>, List<List<String>>>(
        save = { snapshotStateList ->
            // Convert SnapshotStateList<Mutable<String>> to List<List<String>> for saving
            snapshotStateList.map { state -> listOf(state.value) }
        },
        restore = { savedList ->
            // Convert List<List<String>> back to SnapshotStateList<Mutable<String>> on restore
            val restoredList = savedList.map { mutableStateOf(it.first()) }
            SnapshotStateList<MutableState<String>>().apply {
                addAll(restoredList)
            }
        })
    return rememberSaveable(saver = listSaver) {
        mutableStateListOf<MutableState<String>>() // Initial state
    }
}
