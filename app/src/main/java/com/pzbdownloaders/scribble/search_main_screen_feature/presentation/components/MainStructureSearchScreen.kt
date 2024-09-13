package com.pzbdownloaders.scribble.search_main_screen_feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel

@Composable
fun MainStructureSearchScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    screen: String,
    queryText: String
) {
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.primary)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
        ) {
            TopSearchBarSearchScreen(navHostController, viewModel)
            SearchedNotes(viewModel, activity, navHostController, screen, queryText)
        }
    }
}