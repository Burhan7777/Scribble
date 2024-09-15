package com.pzbdownloaders.add_bullet_points_note_feature.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.add_note_feature.domain.model.GetNoteBook
import com.pzbdownloaders.scribble.add_note_feature.presentation.components.AlertDialogBox
import com.pzbdownloaders.scribble.add_note_feature.presentation.components.MainMenu
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel

@Composable
fun BulletPointNote(
    viewModel: MainActivityViewModel,
    navHostController: NavHostController,
    notebookState: MutableState<String>,
    title: MutableState<String>,
    mutableListOfBulletPointsNotes: SnapshotStateList<MutableState<String>>,
) {

    var dialogOpen = remember {
        mutableStateOf(false)
    }

    val notebookText = remember {
        mutableStateOf("")
    }
    var notebook by remember {
        mutableStateOf("")
    }

    var isExpanded = remember {
        mutableStateOf(false)
    }


    viewModel.getNoteBook()


    viewModel.getNoteBook()
    val notebooks: ArrayList<String> =
        arrayListOf("Add Notebook")

//    for (i in listOfNoteBooks?.indices ?: arrayListOf<GetNoteBook>().indices) {
//        notebooks.add(listOfNoteBooks!![i]?.notebook ?: GetNoteBook().notebook)
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.material.MaterialTheme.colors.primary)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (notebook.isNotEmpty()) {
                Text(
                    text = if (notebookState.value.isEmpty()) "Notebook:$notebook" else "Notebook selected: ${notebookState.value}",
                    color = MaterialTheme.colors.onPrimary,
                    fontFamily = FontFamily.fontFamilyRegular,
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(start = 15.dp)
                )
            } else {
                Text(
                    text = if (notebookState.value.isEmpty()) "Add to Notebook" else "Notebook selected: ${notebookState.value}",
                    color = MaterialTheme.colors.onPrimary,
                    fontFamily = FontFamily.fontFamilyRegular,
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(start = 15.dp)
                )
            }

            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Arrow DropDown",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier.clickable {
                    isExpanded.value = true
                })
            if (isExpanded.value) {
                MainMenu(
                    isExpanded = isExpanded,
                    notebookState,
                    viewModel,
                    dialogOpen,
                    notebookText
                )
            }
            //     CreateDropDownMenu("Color", notebookText, notebooks, viewModel, noteBookState)
        }

        androidx.compose.material.TextField(
            value = title.value,
            onValueChange = { title.value = it },
            placeholder = {
                Text(
                    text = "Title",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.fontFamilyBold,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.alpha(0.5f)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = MaterialTheme.colors.primary,
                unfocusedIndicatorColor = MaterialTheme.colors.primary,
                cursorColor = MaterialTheme.colors.onPrimary,
                textColor = androidx.compose.material.MaterialTheme.colors.onPrimary
            ),
            textStyle = TextStyle(fontFamily = FontFamily.fontFamilyBold, fontSize = 25.sp)
        )
        var firstCheckBoxCheck = remember {
            mutableStateOf(true)
        }
        if (firstCheckBoxCheck.value) {
            //CreateCheckBox(firstCheckBoxCheck)
        }

        LazyColumn {
            itemsIndexed(mutableListOfBulletPointsNotes) { indexed, item ->
                SingleRowBulletPoint(
                    text = item,
                    mutableListOfBulletPointsNotes,
                    indexed
                )
            }
        }
    }
}
