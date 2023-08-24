package com.pzbdownloaders.scribble.common.presentation

const val HOME_GRAPH = "home_graph"
const val AUTH_GRAPH = "auth_graph"

sealed class Screens(val route: String) {
    object HomeScreen : Screens("home_screen")
    object AddNoteScreen : Screens("add_note_screen")
    object EditNoteScreen : Screens("edit_note_screen/{id}") {
        fun editNoteWithId(id: String): String {
            return "edit_note_screen/$id"
        }
    }

    object LoginScreen : Screens("login_screen")
    object SignUpScreen : Screens("signup_screen")

    object SplashScreen : Screens("splash_screen")
    object SettingsScreen : Screens("settings_screen")
}