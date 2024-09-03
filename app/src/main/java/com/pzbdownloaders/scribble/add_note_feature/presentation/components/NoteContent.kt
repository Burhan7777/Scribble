package com.pzbdownloaders.scribble.add_note_feature.presentation.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pzbdownloaders.scribble.add_note_feature.domain.model.GetNoteBook
import com.pzbdownloaders.scribble.common.data.Model.NoteBook
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel


@Composable
fun NoteContent(
    title: MutableState<String>,
    content: MutableState<String>,
    viewModel: MainActivityViewModel,
    noteBookState: MutableState<String>,
    showCircularProgress: MutableState<Boolean>,
//    notebook: MutableState<ArrayList<String>>,
//    notebookFromDB: MutableState<ArrayList<NoteBook>>
) {

    var dialogOpen = remember {
        mutableStateOf(false)
    }

    var notebookText = remember {
        mutableStateOf("")
    }


//    val listOfNoteBooks = viewModel.getNoteBooks.observeAsState().value
//    Log.i("notebooks", listOfNoteBooks?.size.toString())



    Row(
    ) {
        CreateDropDownMenu(
            "Choose Notebook",
            notebookText,
            //   notebooks = remember { notebook },
            viewModel = viewModel,
            noteBookState = noteBookState,
            // notebooksFromDB = remember { notebookFromDB }
        )
        //     CreateDropDownMenu("Color", notebookText, notebooks, viewModel, noteBookState)
    }


    TextField(
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
        colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = MaterialTheme.colors.primary,
            unfocusedIndicatorColor = MaterialTheme.colors.primary,
            cursorColor = MaterialTheme.colors.onPrimary
        ),
        textStyle = TextStyle(fontFamily = FontFamily.fontFamilyBold, fontSize = 25.sp)
    )

    TextField(
        value = content.value,
        onValueChange = { content.value = it },
        placeholder = {
            Text(
                text = "Note",
                fontSize = 30.sp,
                fontFamily = FontFamily.fontFamilyExtraLight,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.alpha(0.5f)
            )
        },
        colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = MaterialTheme.colors.primary,
            unfocusedIndicatorColor = MaterialTheme.colors.primary,
            cursorColor = MaterialTheme.colors.onPrimary
        ),
        textStyle = TextStyle(fontFamily = FontFamily.fontFamilyLight, fontSize = 25.sp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDropDownMenu(
    label: String,
    notebookText: MutableState<String>,
    viewModel: MainActivityViewModel,
    noteBookState: MutableState<String>,
//    notebooksFromDB: MutableState<ArrayList<NoteBook>>,
//    notebooks: MutableState<ArrayList<String>>
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var dialogOpen = remember {
        mutableStateOf(false)
    }

    viewModel.getNoteBook()

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = noteBookState.value,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            label = {
                Text(
                    text = label,
                    color = MaterialTheme.colors.onPrimary
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colors.onPrimary,
                unfocusedBorderColor = MaterialTheme.colors.onPrimary,
                focusedTextColor = MaterialTheme.colors.onPrimary,
                focusedContainerColor = androidx.compose.material.MaterialTheme.colors.primary,
                unfocusedContainerColor = androidx.compose.material.MaterialTheme.colors.primary,
                unfocusedTextColor = androidx.compose.material.MaterialTheme.colors.onPrimary
            ),
            shape = RoundedCornerShape(15.dp),
            maxLines = 1,

            )


            DropdownMenu(
                modifier = Modifier
                    .background(MaterialTheme.colors.primaryVariant)
                    .fillMaxWidth().fillMaxHeight()
                    .clickable {
                    },
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
//                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                viewModel.notebooks.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                color = MaterialTheme.colors.onPrimary
                            )
                        },
                        onClick = {
                            if (item == "Add Notebook") {
                                dialogOpen.value = true
                            }
                            noteBookState.value = item
                            isExpanded = !isExpanded
                        },
                        leadingIcon = {
                            if (item == "Add Notebook") {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Add",
                                    tint = androidx.compose.material.MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                    )
                //}
            }
        }
    }

    if (dialogOpen.value) {
        AlertDialogBox(
            notebookText = notebookText,
            viewModel = viewModel,
//            notebooksFromDB = notebooksFromDB,
//            notebooks = notebooks,
            onSaveNotebook = {
                //   notebooks.value.add(notebookText.value)
            },
            onDismiss = {
                dialogOpen.value = false
            }
        )

    }
}

@Composable
fun AlertDialogBox(
    notebookText: MutableState<String>,
    onSaveNotebook: () -> Unit,
    viewModel: MainActivityViewModel,
    onDismiss: () -> Unit,
//    notebooksFromDB: MutableState<ArrayList<NoteBook>>,
//    notebooks: MutableState<ArrayList<String>>,


) {

    val context = LocalContext.current
    androidx.compose.material3.AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        shape = MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(15.dp),
            topEnd = CornerSize(15.dp),
            bottomStart = CornerSize(15.dp),
            bottomEnd = CornerSize(15.dp),
        ),
        containerColor = MaterialTheme.colors.primaryVariant,
        /*      icon = {
                     Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
              }*/

        title = {
            OutlinedTextField(
                value = notebookText.value,
                onValueChange = { notebookText.value = it },
                label = {
                    Text(
                        text = "Add notebook",
                        color = androidx.compose.material.MaterialTheme.colors.onPrimary,
                        fontSize = 15.sp,
                        fontFamily = FontFamily.fontFamilyRegular
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colors.onPrimary,
                    unfocusedTextColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                    focusedTextColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                    unfocusedBorderColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                    cursorColor = androidx.compose.material.MaterialTheme.colors.onPrimary
                ),
                textStyle = TextStyle.Default.copy(
                    fontSize = 15.sp,
                    fontFamily = FontFamily.fontFamilyRegular
                ),
                shape = androidx.compose.material.MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(15.dp),
                    topEnd = CornerSize(15.dp),
                    bottomEnd = CornerSize(15.dp),
                    bottomStart = CornerSize(15.dp),
                )
            )
        },
        confirmButton = {
            androidx.compose.material.Button(
                onClick = {
                    val noteBook = NoteBook(0, notebookText.value)
                    viewModel.addNoteBook(noteBook)
                    viewModel.notebooks.add(notebookText.value)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.onPrimary,
                    contentColor = MaterialTheme.colors.primary
                ),
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(15.dp),
                    topEnd = CornerSize(15.dp),
                    bottomStart = CornerSize(15.dp),
                    bottomEnd = CornerSize(15.dp),
                )
            ) {
                androidx.compose.material.Text(
                    text = "Add",
                    fontFamily = FontFamily.fontFamilyRegular
                )
            }
        },
        dismissButton = {
            androidx.compose.material.OutlinedButton(
                onClick = { onDismiss() },
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(15.dp),
                    topEnd = CornerSize(15.dp),
                    bottomStart = CornerSize(15.dp),
                    bottomEnd = CornerSize(15.dp),
                ),
                border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    contentColor = MaterialTheme.colors.onPrimary
                ),
            ) {
                androidx.compose.material.Text(
                    text = "Cancel",
                    fontFamily = FontFamily.fontFamilyRegular
                )
            }
        },
    )
}

