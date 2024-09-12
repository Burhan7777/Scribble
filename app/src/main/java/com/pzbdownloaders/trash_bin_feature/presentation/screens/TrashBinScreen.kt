package com.pzbdownloaders.trash_bin_feature.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.main_screen.presentation.components.MainStructureMainScreen
import com.pzbdownloaders.trash_bin_feature.presentation.components.MainStructureTrashBinScreen

@Composable
fun TrashBinScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    selectedItem: MutableState<Int>,
    selectedNote: MutableState<Int>
) {

    LaunchedEffect(key1 = true) {
        WindowCompat.setDecorFitsSystemWindows(activity.window, true)
    }

    MainStructureTrashBinScreen(
        navHostController,
        viewModel,
        activity,
        // notebookNavigation,
        selectedItem,
        selectedNote
    )
}
