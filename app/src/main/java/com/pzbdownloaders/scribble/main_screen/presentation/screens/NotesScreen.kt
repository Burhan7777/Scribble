package com.pzbdownloaders.scribble.main_screen.presentation.screens


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pzbdownloaders.scribble.add_note_screen.domain.AddNote
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.main_screen.presentation.components.MainStructureMainScreen

@Composable
fun NotesScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity
) {


    MainStructureMainScreen(navHostController, viewModel, activity)
}

