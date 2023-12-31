package com.pzbdownloaders.scribble.search_feature.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.main_screen.presentation.components.SingleItemNoteList

@Composable
fun SearchedNotes(
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    navHostController: NavHostController,
    screen: String
) {
    val searchResult = viewModel.getSearchResult.observeAsState().value
    val searchArchiveResult = viewModel.getArchiveSearchResult.observeAsState().value


    LazyColumn() {
        item {
            Text(
                text = "Search results",
                fontFamily = FontFamily.fontFamilyBold,
                fontSize = 25.sp,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }

        items(
            searchResult ?: emptyList()
        ) { note ->
            SingleItemNoteList(note = note, navHostController)
        }
        items(
            searchArchiveResult ?: emptyList()
        ) { note ->
            SingleItemNoteList(note = note, navHostController)
        }
    }
}

