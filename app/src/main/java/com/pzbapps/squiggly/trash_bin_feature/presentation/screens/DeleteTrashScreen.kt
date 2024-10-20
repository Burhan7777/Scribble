package com.pzbapps.squiggly.trash_bin_feature.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import com.pzbapps.squiggly.R
import com.pzbapps.squiggly.common.presentation.FontFamily
import com.pzbapps.squiggly.common.presentation.MainActivity
import com.pzbapps.squiggly.common.presentation.MainActivityViewModel
import com.pzbapps.squiggly.main_screen.domain.model.Note
import com.pzbapps.squiggly.trash_bin_feature.presentation.components.AlertBoxes.AlertBoxToDeleteTrashNotes
import com.pzbapps.squiggly.trash_bin_feature.presentation.components.AlertBoxes.AlertBoxToRestoreTrashNotes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteTrashScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    id: Int
) {

    var note = remember { mutableStateOf(Note()) }
    LaunchedEffect(key1 = true) {
        viewModel.getNoteById(id)
    }
    note = viewModel.getNoteById
    var richStateText = mutableStateOf(RichTextState())
    richStateText.value.setHtml(note.value.content)
    var showDeleteDialogBox = remember { mutableStateOf(false) }
    var showRestoreDialogBox = remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.background(MaterialTheme.colors.primary),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colors.primary
                ),
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                },
                actions = {

                    IconButton(onClick = { showRestoreDialogBox.value = true }) {
                        Icon(
                            imageVector = Icons.Filled.Restore,
                            contentDescription = "Restore Note",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                    IconButton(onClick = { showDeleteDialogBox.value = true }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete Note Forever",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                })
        }) {
        if (note.value.listOfCheckedNotes.size == 0 && note.value.listOfBulletPointNotes.size == 0) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primary)
            ) {
                TextField(
                    value = note.value.title,
                    onValueChange = { },
                    placeholder = {
                        Text(
                            text = "Title",
                            fontSize = 20.sp,
                            fontFamily = FontFamily.fontFamilyBold,
                            color = MaterialTheme.colors.onPrimary,
                            modifier = Modifier.alpha(0.5f)
                        )
                    },
                    colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        focusedIndicatorColor = MaterialTheme.colors.primary,
                        unfocusedIndicatorColor = MaterialTheme.colors.primary,
                        cursorColor = MaterialTheme.colors.onPrimary,
                        textColor = MaterialTheme.colors.onPrimary,
                        disabledTextColor = MaterialTheme.colors.onPrimary
                    ),
                    textStyle = TextStyle(fontFamily = FontFamily.fontFamilyBold, fontSize = 20.sp),
                    enabled = false
                )

                RichTextEditor(
                    state = richStateText.value,
                    colors = RichTextEditorDefaults.richTextEditorColors(
                        containerColor = MaterialTheme.colors.primary,
                        cursorColor = MaterialTheme.colors.onPrimary,
                        textColor = MaterialTheme.colors.onPrimary,
                        focusedIndicatorColor = MaterialTheme.colors.primary,
                        unfocusedIndicatorColor = MaterialTheme.colors.primary,
                        disabledTextColor = MaterialTheme.colors.onPrimary
                    ),
                    placeholder = {
                        Text(
                            text = "Note",
                            fontSize = 18.sp,
                            fontFamily = FontFamily.fontFamilyBold,
                            color = MaterialTheme.colors.onPrimary,
                            modifier = Modifier.alpha(0.5f)
                        )
                    },
                    textStyle = TextStyle(
                        fontFamily = FontFamily.fontFamilyRegular,
                        fontSize = 18.sp
                    ),
                    enabled = false
                )
                if (showDeleteDialogBox.value) {
                    AlertBoxToDeleteTrashNotes(
                        note = note.value,
                        viewModel = viewModel,
                        navHostController = navHostController
                    ) {
                        showDeleteDialogBox.value = false
                    }
                }
                if (showRestoreDialogBox.value) {
                    AlertBoxToRestoreTrashNotes(
                        navHostController = navHostController,
                        viewModel = viewModel,
                        activity = activity,
                        note = note.value
                    ) {
                        showRestoreDialogBox.value = false
                    }
                }
            }
        } else if (note.value.listOfCheckedNotes.size > 0 && note.value.listOfBulletPointNotes.size == 0) {


            var checkBox = rememberSaveable { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primary)
                    .padding(it)
            ) {

                TextField(
                    value = note.value.title,
                    onValueChange = { },
                    placeholder = {
                        Text(
                            text = "Title",
                            fontSize = 20.sp,
                            fontFamily = FontFamily.fontFamilyBold,
                            color = MaterialTheme.colors.onPrimary,
                            modifier = Modifier.alpha(0.5f)
                        )
                    },
                    colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        focusedIndicatorColor = MaterialTheme.colors.primary,
                        unfocusedIndicatorColor = MaterialTheme.colors.primary,
                        cursorColor = MaterialTheme.colors.onPrimary,
                        textColor = MaterialTheme.colors.onPrimary,
                        disabledTextColor = MaterialTheme.colors.onPrimary,
                    ),
                    textStyle = TextStyle(fontFamily = FontFamily.fontFamilyBold, fontSize = 20.sp),
                    enabled = false
                )

                for (i in note.value.listOfCheckedNotes.indices) {
                    checkBox.value = note.value.listOfCheckedBoxes[i]

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checkBox.value,
                            onCheckedChange = {
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colors.onPrimary,
                                checkmarkColor = MaterialTheme.colors.onSecondary,
                                uncheckedColor = MaterialTheme.colors.onPrimary,
                                disabledCheckedColor = MaterialTheme.colors.onPrimary,
                                disabledUncheckedColor = MaterialTheme.colors.onPrimary
                            ),
                            enabled = false
                        )
                        OutlinedTextField(
                            value = note.value.listOfCheckedNotes[i],
                            onValueChange = {

                            },
                            colors = androidx.compose.material3.TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colors.primary,
                                unfocusedContainerColor = MaterialTheme.colors.primary,
                                focusedTextColor = MaterialTheme.colors.onPrimary,
                                unfocusedTextColor = MaterialTheme.colors.onPrimary,
                                unfocusedIndicatorColor = MaterialTheme.colors.primary,
                                focusedIndicatorColor = MaterialTheme.colors.primary,
                                cursorColor = MaterialTheme.colors.onPrimary,
                                disabledContainerColor = MaterialTheme.colors.primary,
                                disabledTextColor = MaterialTheme.colors.onPrimary
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                fontFamily = FontFamily.fontFamilyRegular
                            ),
                            enabled = false
                        )
                    }
                }
                if (showDeleteDialogBox.value) {
                    AlertBoxToDeleteTrashNotes(
                        note = note.value,
                        viewModel = viewModel,
                        navHostController = navHostController
                    ) {
                        showDeleteDialogBox.value = false
                    }
                }
                if (showRestoreDialogBox.value) {
                    AlertBoxToRestoreTrashNotes(
                        navHostController = navHostController,
                        viewModel = viewModel,
                        activity = activity,
                        note = note.value
                    ) {
                        showRestoreDialogBox.value = false
                    }
                }
            }
        } else if (note.value.listOfBulletPointNotes.size > 0 && note.value.listOfCheckedNotes.size == 0) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primary)
                    .padding(it)
            ) {

                TextField(
                    value = note.value.title,
                    onValueChange = { },
                    placeholder = {
                        Text(
                            text = "Title",
                            fontSize = 20.sp,
                            fontFamily = FontFamily.fontFamilyBold,
                            color = MaterialTheme.colors.onPrimary,
                            modifier = Modifier.alpha(0.5f)
                        )
                    },
                    colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        focusedIndicatorColor = MaterialTheme.colors.primary,
                        unfocusedIndicatorColor = MaterialTheme.colors.primary,
                        cursorColor = MaterialTheme.colors.onPrimary,
                        textColor = MaterialTheme.colors.onPrimary,
                        disabledTextColor = MaterialTheme.colors.onPrimary,
                    ),
                    textStyle = TextStyle(fontFamily = FontFamily.fontFamilyBold, fontSize = 20.sp),
                    enabled = false
                )

                for (i in note.value.listOfBulletPointNotes.indices) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painterResource(id = R.drawable.bullet_point),
                            contentDescription = "Bullet Point",
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
                        )
                        OutlinedTextField(
                            value = note.value.listOfBulletPointNotes[i],
                            onValueChange = {
                                // note.value = it
                            },

                            colors = androidx.compose.material3.TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colors.primary,
                                unfocusedContainerColor = MaterialTheme.colors.primary,
                                focusedTextColor = MaterialTheme.colors.onPrimary,
                                unfocusedTextColor = MaterialTheme.colors.onPrimary,
                                unfocusedIndicatorColor = MaterialTheme.colors.primary,
                                focusedIndicatorColor = MaterialTheme.colors.primary,
                                cursorColor = MaterialTheme.colors.onPrimary,
                                disabledTextColor = MaterialTheme.colors.onPrimary,
                                disabledContainerColor = MaterialTheme.colors.primary
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                fontFamily = FontFamily.fontFamilyRegular
                            ),
                            enabled = false
                        )
                    }
                }
                if (showDeleteDialogBox.value) {
                    AlertBoxToDeleteTrashNotes(
                        note = note.value,
                        viewModel = viewModel,
                        navHostController = navHostController
                    ) {
                        showDeleteDialogBox.value = false
                    }
                }
                if (showRestoreDialogBox.value) {
                    AlertBoxToRestoreTrashNotes(
                        navHostController = navHostController,
                        viewModel = viewModel,
                        activity = activity,
                        note = note.value
                    ) {
                        showRestoreDialogBox.value = false
                    }
                }
            }
        }
    }
}