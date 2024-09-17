package com.pzbdownloaders.scribble.add_checkbox_note_feature.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.add_checkbox_note_feature.presentation.components.MainStructureCheckBoxNote
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.main_screen.presentation.components.MainStructureMainScreen

@Composable
fun CheckboxNoteMainScreen(
    navHostController: NavHostController,
    mainActivityViewModel: MainActivityViewModel,
    activity: MainActivity
) {

    var notebookState = remember {
        mutableStateOf("")
    }

    var title = rememberSaveable {
        mutableStateOf("")
    }

    var note = RememberSaveableSnapshotStateList()

    var mutableListConverted = rememberSaveable {
        ArrayList<String>()
    }
    var mutableListOfCheckBoxes = rememberSaveable { ArrayList<Boolean>() }

    LaunchedEffect(key1 = true) {
        if (note.isEmpty()) {
            note.add(mutableStateOf(""))
        }

    }


    LaunchedEffect(key1 = note.size) {
        mutableListOfCheckBoxes.add(false)
    }

    MainStructureCheckBoxNote(
        navController = navHostController,
        viewModel = mainActivityViewModel,
        notebookState,
        title,
        activity = activity,
        note,
        mutableListConverted,
        mutableListOfCheckBoxes,
    )
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