package com.pzbdownloaders.scribble.add_note_feature.presentation.components

import android.util.Log
import android.util.Size
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius.Companion.Zero
import androidx.compose.ui.geometry.Offset.Companion.Zero
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.R
import com.pzbdownloaders.scribble.add_note_feature.domain.model.GetNoteBook
import com.pzbdownloaders.scribble.common.domain.utils.GetResult
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel


@Composable
fun NoteContent(
    title: MutableState<String>,
    content: MutableState<String>,
    viewModel: MainActivityViewModel,
    noteBookState: MutableState<String>
) {

    var dialogOpen = remember {
        mutableStateOf(false)
    }

    var notebookText = remember {
        mutableStateOf("")
    }

    val listOfNoteBooks = viewModel.getNoteBooks.observeAsState().value
    Log.i("notebooks", listOfNoteBooks?.size.toString())

    viewModel.getNoteBook()
    var notebooks: ArrayList<String> =
        arrayListOf("Add Notebook")

    for (i in listOfNoteBooks?.indices ?: arrayListOf<GetNoteBook>().indices) {
        notebooks.add(listOfNoteBooks!![i]?.notebook ?: GetNoteBook().notebook)
    }


    Row(
    ) {
        CreateDropDownMenu("Choose Notebook", notebookText, notebooks, viewModel, noteBookState)
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
    notebooks: ArrayList<String>,
    viewModel: MainActivityViewModel,
    noteBookState: MutableState<String>
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
                .fillMaxWidth(),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            notebooks.forEach { item ->
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
            }
        }
    }

    if (dialogOpen.value) {
        AlertDialogBox(
            notebookText = notebookText,
            viewModel = viewModel,
            onSaveNotebook = {
                notebooks.add(notebookText.value)
            }
        ) {
            dialogOpen.value = false
        }
    }
}

@Composable
fun AlertDialogBox(
    notebookText: MutableState<String>,
    onSaveNotebook: () -> Unit,
    viewModel: MainActivityViewModel,
    onDismiss: () -> Unit,

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
                    //    onSaveNotebook()
                    viewModel.addNoteBook(notebookText.value)
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

