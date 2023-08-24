package com.pzbdownloaders.scribble.edit_screen.presentation.screens


import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.edit_screen.presentation.components.MainStructureEditNote
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import com.pzbdownloaders.scribble.main_screen.presentation.components.MainStructureMainScreen
import java.util.*


@Composable
fun EditNoteScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    id: String
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
        activity
    )
    Log.i("stuff", "stuff")
}


