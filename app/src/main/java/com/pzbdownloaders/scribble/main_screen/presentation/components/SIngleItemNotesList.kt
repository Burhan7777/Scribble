package com.pzbdownloaders.scribble.main_screen.presentation.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.add_note_feature.domain.model.AddNote
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.Screens
import com.pzbdownloaders.scribble.main_screen.domain.model.Note

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SingleItemNoteList(note: Note, navHostController: NavHostController) {
    if (!note.archive && !note.locked) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(10.dp)
                .border(
                    BorderStroke(1.dp, androidx.compose.material.MaterialTheme.colors.onPrimary),
                    androidx.compose.material.MaterialTheme.shapes.medium.copy(
                        topStart = CornerSize(10.dp),
                        topEnd = CornerSize(10.dp),
                        bottomStart = CornerSize(10.dp),
                        bottomEnd = CornerSize(10.dp),
                    )
                )
                .clickable {
                    navHostController.navigate(
                        Screens.EditNoteScreen.editNoteWithId(
                            note.id,
                            Constant.HOME
                        )
                    )
                    Log.i("title", note.title)
                },
            shape = MaterialTheme.shapes.medium.copy(
                topStart = CornerSize(10.dp),
                topEnd = CornerSize(10.dp),
                bottomStart = CornerSize(10.dp),
                bottomEnd = CornerSize(10.dp),
            ),
            elevation = CardDefaults.cardElevation(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = androidx.compose.material.MaterialTheme.colors.primary,
                contentColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                disabledContainerColor = androidx.compose.material.MaterialTheme.colors.primary,
                disabledContentColor = androidx.compose.material.MaterialTheme.colors.onPrimary
            )
        ) {
            Text(
                text = note.title,
                modifier = Modifier.padding(10.dp),
                fontSize = 25.sp,
                fontFamily = FontFamily.fontFamilyBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = note.content,
                modifier = Modifier.padding(10.dp),
                fontSize = 15.sp,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily.fontFamilyLight
            )
        }
    } else if (note.listOfCheckedBoxes.size > 0) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in note.listOfCheckedNotes.indices) {
                Checkbox(
                    checked = note.listOfCheckedBoxes[i],
                    {
                        note.listOfCheckedBoxes[i] = it
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                        checkmarkColor = androidx.compose.material.MaterialTheme.colors.onSecondary,
                        uncheckedColor = androidx.compose.material.MaterialTheme.colors.onPrimary
                    )
                )
                OutlinedTextField(
                    value = note.listOfCheckedNotes[i],
                    onValueChange = { note.listOfCheckedNotes[i] = it },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { }
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = androidx.compose.material.MaterialTheme.colors.primary,
                        unfocusedContainerColor = androidx.compose.material.MaterialTheme.colors.primary,
                        focusedTextColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                        unfocusedTextColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                        unfocusedIndicatorColor = androidx.compose.material.MaterialTheme.colors.primary,
                        focusedIndicatorColor = androidx.compose.material.MaterialTheme.colors.primary,
                        cursorColor = androidx.compose.material.MaterialTheme.colors.onPrimary
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        fontFamily = FontFamily.fontFamilyRegular
                    ),
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            if (it.isFocused) {
                                keyboardController?.show()
                            }
                        },
                    trailingIcon = {
                        IconButton(onClick = { note.listOfCheckedNotes.removeAt(i) }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear checkbox",
                                tint = androidx.compose.material.MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                )

            }
        }
    }
}
