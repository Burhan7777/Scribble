package com.pzbdownloaders.scribble.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.login_and_signup_feature.authGraph

@Composable
fun NavHost(
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    result: String,
    selectedItem: MutableState<Int>,
    selectedNote: MutableState<Int>
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
      //  startDestination = if (result == Constant.USER_VALUE) HOME_GRAPH else AUTH_GRAPH
        startDestination = HOME_GRAPH
    ) {
        authGraph(navHostController = navController, viewModel, activity, result)
        homeGraph(
            navController = navController,
            viewModel = viewModel,
            activity = activity,
            result,
            selectedItem,
            selectedNote
        )
    }
}