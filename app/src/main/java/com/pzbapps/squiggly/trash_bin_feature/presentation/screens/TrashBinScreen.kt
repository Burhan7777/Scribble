package com.pzbapps.squiggly.trash_bin_feature.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.pzbapps.squiggly.common.presentation.MainActivity
import com.pzbapps.squiggly.common.presentation.MainActivityViewModel
import com.pzbapps.squiggly.trash_bin_feature.presentation.components.MainStructureTrashBinScreen

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
