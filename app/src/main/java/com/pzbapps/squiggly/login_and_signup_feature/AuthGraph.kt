package com.pzbapps.squiggly.login_and_signup_feature

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.pzbapps.squiggly.common.presentation.*
import com.pzbapps.squiggly.login_and_signup_feature.presentation.LoginScreen
import com.pzbapps.squiggly.login_and_signup_feature.sign_up.SignUpScreen


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