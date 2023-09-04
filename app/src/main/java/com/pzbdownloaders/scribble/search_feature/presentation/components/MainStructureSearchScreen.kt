package com.pzbdownloaders.scribble.search_feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Note
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.common.presentation.Screens
import com.pzbdownloaders.scribble.main_screen.presentation.components.Notes
import com.pzbdownloaders.scribble.main_screen.presentation.components.TopSearchBar
import kotlinx.coroutines.launch

@Composable
fun MainStructureSearchScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    screen: String
) {
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.primary)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize().background(MaterialTheme.colors.primary)
        ) {
            TopSearchBarSearchScreen(navHostController, viewModel)
            SearchedNotes(viewModel, activity, navHostController, screen)
        }
    }
}