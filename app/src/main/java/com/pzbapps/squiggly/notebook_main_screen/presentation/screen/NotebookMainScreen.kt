package com.pzbapps.squiggly.notebook_main_screen.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.pzbapps.squiggly.common.presentation.MainActivity
import com.pzbapps.squiggly.common.presentation.MainActivityViewModel
import com.pzbapps.squiggly.notebook_main_screen.presentation.components.MainStructureNotebookScreen

@Composable
fun NotebookMainScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    title: String,
    selectedItem: MutableState<Int>,
    selectedNote: MutableState<Int>
) {

    viewModel.getNoteBook()
    val notebooks = viewModel.getNoteBooks.observeAsState().value
    val notebookNavigation: ArrayList<String> = ArrayList()
    for (i in notebooks?.indices ?: emptyList<String>().indices) {
        notebookNavigation.add(notebooks!![i]?.notebook ?: "")
    }
    MainStructureNotebookScreen(
        navHostController,
        viewModel,
        activity,
        notebookNavigation,
        title,
        selectedItem,
        selectedNote
    )
}