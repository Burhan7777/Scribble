package com.pzbdownloaders.scribble.add_note_feature.presentation.components

import android.app.Activity
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlignHorizontalCenter
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatAlignJustify
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.FormatAlignRight
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.TextDecrease
import androidx.compose.material.icons.filled.TextIncrease
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHostController
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.pzbdownloaders.scribble.add_note_feature.domain.model.AddNote
import com.pzbdownloaders.scribble.common.data.Model.NoteBook
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.common.presentation.components.AlertDialogBoxTrialEnded
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import io.ktor.util.reflect.typeInfoImpl

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun MainStructureAddNote(
    navController: NavHostController,
    title: MutableState<String>,
    content: MutableState<String>,
    viewModel: MainActivityViewModel,
    //note: Note,
    notebookState: MutableState<String>,
    activity: MainActivity,
    richTextState: MutableState<RichTextState>
//    notebook: MutableState<ArrayList<String>>,
//    notebookFromDB: MutableState<ArrayList<NoteBook>>
) {

    var showCircularProgress = remember { mutableStateOf(true) }
    var context = LocalContext.current
    var showTrialEndedDialogBox = remember { mutableStateOf(false) }
    var boldText = remember { mutableStateOf(false) }
    var underlineText = remember { mutableStateOf(false) }
    var italicText = remember { mutableStateOf(false) }

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



    WindowCompat.setDecorFitsSystemWindows(activity.window, false)


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
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Undo")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        var note = Note(
                            0,
                            title.value,
                            content = richTextState.value.toHtml(),
                            false,
                            notebookState.value,
                            timeStamp = 123,
                            locked = false

                        )
                            viewModel.insertNote(note)
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
                NoteContent(
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
                BottomTextFormattingBar(
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

