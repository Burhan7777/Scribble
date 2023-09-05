package com.pzbdownloaders.scribble.main_screen.presentation.screens


import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.main_screen.presentation.components.MainStructureMainScreen

@Composable
fun NotesScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity
) {

    // WE GET THE NOTEBOOKS HERE BECAUSE WE HAVE TO DISPLAY THEM IN THE NAVIGATION  VIEW

    viewModel.getNoteBook()
    val notebooks = viewModel.getNoteBooks.observeAsState().value
    val notebookNavigation: ArrayList<String> = ArrayList()
    for (i in notebooks?.indices ?: emptyList<String>().indices) {
        notebookNavigation.add(notebooks!![i]?.notebook ?: "")
    }
    MainStructureMainScreen(navHostController, viewModel, activity, notebookNavigation)
}

