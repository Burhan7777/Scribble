package com.pzbdownloaders.scribble.archive_notes_feature.presentation.screen

import androidx.compose.runtime.Composable
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
) {
    MainStructureArchiveScreen(
        navHostController = navHostController,
        viewModel = viewModel,
        activity = activity
    )
}