package com.pzbdownloaders.scribble.common.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.login_and_signup_feature.authGraph

@Composable
fun NavHost(
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    result: String
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = if (result == Constant.USER_VALUE) HOME_GRAPH else AUTH_GRAPH
    ) {
        authGraph(navHostController = navController, viewModel, activity, result)
        homeGraph(navController = navController, viewModel = viewModel, activity = activity,result)
    }
}