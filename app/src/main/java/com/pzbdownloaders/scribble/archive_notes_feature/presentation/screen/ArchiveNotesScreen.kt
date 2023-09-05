package com.pzbdownloaders.scribble.archive_notes_feature.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.archive_notes_feature.presentation.components.MainStructureArchiveScreen
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.main_screen.presentation.components.MainStructureMainScreen

@Composable
fun ArchiveNotesScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    selectedItem: MutableState<Int>,
    selectedNote: MutableState<Int>
) {
    viewModel.getNoteBook()
    val notebooks = viewModel.getNoteBooks.observeAsState().value
    val notebookNavigation: ArrayList<String> = ArrayList()
    for (i in notebooks?.indices ?: emptyList<String>().indices) {
        notebookNavigation.add(notebooks!![i]?.notebook ?: "")
    }
    MainStructureArchiveScreen(
        navHostController = navHostController,
        viewModel = viewModel,
        activity = activity,
        notebookNavigation,
        selectedItem,
        selectedNote
    )
}