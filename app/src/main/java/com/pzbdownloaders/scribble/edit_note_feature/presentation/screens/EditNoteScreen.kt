package com.pzbdownloaders.scribble.edit_note_feature.presentation.screens


import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.MainStructureEditNote


@Composable
fun EditNoteScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    id: Int,
    screen: String
) {

    // viewModel.getNoteById(id)
    // val note = viewModel.getNote


    // Log.i("note", note.toString())
    var title = remember {
        mutableStateOf("")
    }

    var content = remember {
        mutableStateOf("")
    }


//      Log.i("mutable", note.value.title)
//  Log.i("mutable", note.value.content)

//Log.i("mutableString", title.value)
//  Log.i("mutableString", content.value)

//   Log.i("getnote", note.toString())

    MainStructureEditNote(
        navController = navHostController,
        viewModel = viewModel,
        id = id,
        activity,
        screen
    )
}


