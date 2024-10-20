package com.pzbapps.squiggly.search_main_screen_feature.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.pzbapps.squiggly.common.presentation.MainActivity
import com.pzbapps.squiggly.common.presentation.MainActivityViewModel
import com.pzbapps.squiggly.search_main_screen_feature.presentation.components.MainStructureSearchScreen

@Composable
fun SearchScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    screen: String,
    queryText: String
) {
    MainStructureSearchScreen(
        navHostController = navHostController,
        viewModel = viewModel,
        activity = activity,
        screen = screen,
        queryText = queryText
    )
}