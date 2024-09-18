package com.pzbdownloaders.scribble.add_checkbox_note_feature.presentation.components

import android.view.ViewTreeObserver
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.common.presentation.components.AlertDialogBoxTrialEnded
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import kotlin.concurrent.schedule

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainStructureCheckBoxNote(
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    notebookState: MutableState<String>,
    title: MutableState<String>,
    activity: MainActivity,
    listOfCheckedNotes: SnapshotStateList<MutableState<String>>,
    mutableListConverted: ArrayList<String>,
    mutableListOfCheckBoxes: ArrayList<Boolean>
) {
    var context = LocalContext.current
    var generatedNoteId = rememberSaveable { mutableStateOf<Long>(0) }

//    var mutableListOfCheckboxTexts = remember {
//        mutableStateListOf<MutableState<String>>()
//    }


//    if(equate.value) {
//        mutableListOfCheckboxTexts = RememberSaveableSnapshotStateList()
//       equate.value = false
    // }

//    LaunchedEffect(key1 = Unit) {
//        mutableListOfCheckBoxes.add(false)
//        mutableListOfCheckBoxes.removeLast()
//    }


    var count = remember {
        mutableStateOf(0)

    }

    var showTrialEndedDialogBox = remember {
        mutableStateOf(
            false
        )
    }


    if (showTrialEndedDialogBox.value) {
        AlertDialogBoxTrialEnded {
            showTrialEndedDialogBox.value = false
        }
    }

    if (generatedNoteId.value.toInt() == 0) {
        LaunchedEffect(key1 = true) {
            var note = Note(
                0,
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
            listOfCheckedNotes,
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
            notebook = notebookState.value,
            listOfCheckedNotes = mutableListConverted,
            listOfCheckedBoxes = mutableListOfCheckBoxes,
        )
        println("triggered")
        viewModel.updateNote(note1)
    }

//    LaunchedEffect(key1 = mutableListOfCheckboxTexts.size > 0) {
//        mutableListOfCheckBoxes.add(false)
//    }

    var remember = rememberCoroutineScope()
    BackHandler {
        // keyboardController?.hide()
        remember.launch(Dispatchers.Main) {
            count.value++
            delay(1000)
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colors.primary
                ),
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = {
                        convertMutableStateIntoString(
                            listOfCheckedNotes,
                            mutableListConverted
                        )
                        val note = Note(
                            id = generatedNoteId.value.toInt(),
                            title = title.value,
                            notebook = notebookState.value,
                            listOfCheckedNotes = mutableListConverted,
                            listOfCheckedBoxes = mutableListOfCheckBoxes,
                            timeStamp = System.currentTimeMillis(),
                            timeModified = System.currentTimeMillis()
                        )
                        viewModel.updateNote(note)
                        navController.popBackStack()
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
                        convertMutableStateIntoString(
                            listOfCheckedNotes,
                            mutableListConverted
                        )
                        val note = Note(
                            id = generatedNoteId.value.toInt(),
                            title = title.value,
                            notebook = notebookState.value,
                            listOfCheckedNotes = mutableListConverted,
                            listOfCheckedBoxes = mutableListOfCheckBoxes,
                            timeStamp = System.currentTimeMillis(),
                            timeModified = System.currentTimeMillis()
                        )
                        viewModel.updateNote(note)
                        Toast.makeText(activity, "Note has been saved", Toast.LENGTH_SHORT)
                            .show()
                        navController.popBackStack()

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
            CheckboxNote(
                viewModel,
                navController,
                notebookState,
                title,
                listOfCheckedNotes,
                mutableListOfCheckBoxes,
                count
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

