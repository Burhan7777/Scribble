package com.pzbdownloaders.scribble.notebook_main_screen.presentation.components.addNoteInNotebookComponents

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mohamedrejeb.richeditor.model.RichTextState
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.common.presentation.components.AlertDialogBoxTrialEnded
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import java.util.Timer
import kotlin.concurrent.schedule

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun MainStructureAddNoteInNotebook(
    navController: NavHostController,
    title: MutableState<String>,
    content: MutableState<String>,
    viewModel: MainActivityViewModel,
    //note: Note,
    notebookState: MutableState<String>,
    activity: MainActivity,
    richTextState: MutableState<RichTextState>,
    notebookName: String
//    notebook: MutableState<ArrayList<String>>,
//    notebookFromDB: MutableState<ArrayList<NoteBook>>
) {

    var showCircularProgress = remember { mutableStateOf(true) }
    var context = LocalContext.current
    var showTrialEndedDialogBox = remember { mutableStateOf(false) }
    var boldText = remember { mutableStateOf(false) }
    var underlineText = remember { mutableStateOf(false) }
    var italicText = remember { mutableStateOf(false) }
    var generatedNoteId = remember { mutableStateOf<Long>(0) }

    var annotatedString = remember { mutableStateOf(AnnotatedString("")) }

    var textFieldValue =
        remember { mutableStateOf<TextFieldValue>(TextFieldValue(annotatedString.value)) }

    val keyboardController = LocalSoftwareKeyboardController.current


    var boldSelection = remember { mutableStateOf(false) }

    var isBoldActivated = remember { mutableStateOf(false) }
    var isUnderlineActivated = remember { mutableStateOf(false) }
    var isItalicActivated = remember { mutableStateOf(false) }
    var isOrderedListActivated = remember { mutableStateOf(false) }
    var isUnOrderedListActivated = remember { mutableStateOf(false) }
    var showFontSize = remember { mutableStateOf(false) }
    var fontSize = remember { mutableStateOf("20") }

    if (richTextState.value.annotatedString.text == "") fontSize.value = "20"


    DisposableEffect(Unit) {
        var note = Note(
            0,
            title = title.value,
            content = richTextState.value.toHtml(),
            timeModified = System.currentTimeMillis(),
            notebook = notebookName,
            timeStamp = System.currentTimeMillis()
//                listOfBulletPointNotes = convertedBulletPoints,
//                listOfCheckedNotes = converted,
//                listOfCheckedBoxes = mutableListOfCheckBoxes

        )
        viewModel.insertNote(note)
        viewModel.generatedNoteId.observe(activity) {
            generatedNoteId.value = it
        }
        val timer = Timer()
        // Schedule a task to run every 10 seconds
        timer.schedule(delay = 3000L, period = 1000L) {
            viewModel.getNoteById(generatedNoteId.value.toInt())
            var noteFromDb = viewModel.getNoteById
            var note1 = noteFromDb.value.copy(
                title = title.value,
                content = richTextState.value.toHtml(),
                timeModified = System.currentTimeMillis(),
                notebook = notebookName,
//                listOfBulletPointNotes = convertedBulletPoints,
//                listOfCheckedNotes = converted,
//                listOfCheckedBoxes = mutableListOfCheckBoxes

            )
            viewModel.updateNote(note1)
        }

        // Clean up the timer when the composable leaves the composition
        onDispose {
            timer.cancel() // Stop the timer
        }
    }

    // WindowCompat.setDecorFitsSystemWindows(activity.window, false)


    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colors.primary
                ),
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "go back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.getNoteById(generatedNoteId.value.toInt())
                        var noteFromDb = viewModel.getNoteById
                        var note2 = noteFromDb.value.copy(
                            title = title.value,
                            content = richTextState.value.toHtml(),
                            timeModified = System.currentTimeMillis(),
                            notebook = notebookName,
                            timeStamp = System.currentTimeMillis()
//                listOfBulletPointNotes = convertedBulletPoints,
//                listOfCheckedNotes = converted,
//                listOfCheckedBoxes = mutableListOfCheckBoxes

                        )
                        viewModel.updateNote(note2)
                        Toast.makeText(context, "Note has been added", Toast.LENGTH_SHORT)
                            .show()
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "Save")
                    }
                    if (showTrialEndedDialogBox.value) {
                        AlertDialogBoxTrialEnded {
                            showTrialEndedDialogBox.value = false
                        }
                    }
                }
            )
        },
//        bottomBar = {
//            BottomAppBar(
//                modifier = if (WindowInsets.isImeVisible) Modifier.padding(keyboardHeight) else Modifier.padding(
//                    10.dp).imePadding()
//            ) {
//
//            }
//        }

    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(it)) {
                NoteContentNoteInNotebook(
                    title,
                    content,
                    viewModel,
                    notebookState,
                    showCircularProgress,
                    textFieldValue,
                    boldText,
                    richTextState.value,
//                notebook,
//                notebookFromDB)
                )
            }
            Box(
                modifier =
                Modifier
                    .padding(WindowInsets.ime.asPaddingValues())
                    .padding(15.dp)
                    .background(MaterialTheme.colors.primaryVariant)
                    .fillMaxWidth()
                    .height(if (showFontSize.value) 100.dp else 50.dp)
                    .align(
                        Alignment.BottomCenter
                    )
            ) {
                BottomTextFormattingBarNoteInNotebook(
                    showFontSize = showFontSize,
                    fontSize = fontSize,
                    richTextState = richTextState,
                    isBoldActivated = isBoldActivated,
                    isUnderlineActivated = isUnderlineActivated,
                    isItalicActivated = isItalicActivated,
                    isOrderedListActivated = isOrderedListActivated,
                    isUnOrderedListActivated = isUnOrderedListActivated
                )
            }
        }
    }

}

fun makeTextBold(textFieldValue: MutableState<TextFieldValue>) {
    val selection = textFieldValue.value.selection
    if (!selection.collapsed) {
        val annotatedString = buildAnnotatedString {
            append(textFieldValue.value.annotatedString.subSequence(0, selection.start))

            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(
                    textFieldValue.value.annotatedString.subSequence(
                        selection.start,
                        selection.end
                    )
                )
            }

            append(
                textFieldValue.value.annotatedString.subSequence(
                    selection.end,
                    textFieldValue.value.annotatedString.length
                )
            )


        }

        textFieldValue.value = textFieldValue.value.copy(annotatedString)
    }
}

