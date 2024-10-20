package com.pzbapps.squiggly.archive_notes_feature.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.pzbapps.squiggly.archive_notes_feature.presentation.components.MainStructureArchiveScreen
import com.pzbapps.squiggly.common.presentation.MainActivity
import com.pzbapps.squiggly.common.presentation.MainActivityViewModel

@Composable
fun ArchiveNotesScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    selectedItem: MutableState<Int>,
    selectedNote: MutableState<Int>
) {
    WindowCompat.setDecorFitsSystemWindows(activity.window, true)
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