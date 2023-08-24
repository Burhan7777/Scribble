package com.pzbdownloaders.scribble.login_and_signup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.pzbdownloaders.scribble.common.presentation.*
import com.pzbdownloaders.scribble.login_and_signup.presentation.LoginScreen
import com.pzbdownloaders.scribble.login_and_signup.sign_up.SignUpScreen


fun NavGraphBuilder.authGraph(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    result: String?
) {
    navigation(
        route = AUTH_GRAPH,
        startDestination = Screens.LoginScreen.route
    ) {
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navHostController, viewModel, activity)
        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navHostController, viewModel, activity)
        }
    }
}