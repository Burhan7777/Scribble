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
import com.pzbdownloaders.scribble.common.domain.utils.trialPeriodExists
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
                        if (trialPeriodExists.value != Constant.TRIAL_ENDED) {
                            viewModel.insertNote(note)
                            Toast.makeText(context, "Note has been added", Toast.LENGTH_SHORT)
                                .show()
                            navController.popBackStack()
                        } else {
                            showTrialEndedDialogBox.value = true
                        }
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
                Column() {
                    if (showFontSize.value) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            modifier = Modifier.width(200.dp)
                        ) {
                            androidx.compose.material3.IconButton(onClick = {
                                if (fontSize.value > "0") {
                                    fontSize.value = (fontSize.value.toInt() - 1).toString()
                                    richTextState.value.toggleSpanStyle(
                                        SpanStyle(fontSize = fontSize.value.toInt().sp)
                                    )
                                }
                            }) {
                                androidx.compose.material3.Icon(
                                    imageVector = Icons.Filled.TextDecrease,
                                    contentDescription = "Decrease font size",
                                    tint = MaterialTheme.colors.onPrimary
                                )
                            }
                            TextField(
                                value = fontSize.value,
                                onValueChange = { fontSize.value = it },
                                modifier = Modifier
                                    .width(50.dp)
                                    .background(MaterialTheme.colors.primaryVariant),
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = MaterialTheme.colors.primaryVariant,
                                    unfocusedIndicatorColor = MaterialTheme.colors.primaryVariant,
                                    textColor = MaterialTheme.colors.onPrimary
                                ),
                                enabled = false
                            )
                            androidx.compose.material3.IconButton(onClick = {
                                fontSize.value = (fontSize.value.toInt() + 1).toString()
                                richTextState.value.toggleSpanStyle(
                                    SpanStyle(fontSize = fontSize.value.toInt().sp)
                                )
                            }) {
                                androidx.compose.material3.Icon(
                                    imageVector = Icons.Filled.TextIncrease,
                                    contentDescription = "increase  font size",
                                    tint = MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.horizontalScroll(rememberScrollState())
                    ) {
                        IconButton(
                            onClick = {
                                richTextState.value.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                                isBoldActivated.value = !isBoldActivated.value
                            },
                            modifier = Modifier
                                .clip(
                                    if (isBoldActivated.value) CircleShape else MaterialTheme.shapes.medium.copy(
                                        all = CornerSize(0.dp)
                                    )
                                )
                                .background(if (isBoldActivated.value) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primaryVariant)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colors.primaryVariant,
                                    shape = CircleShape
                                )
                        )
                        {
                            Icon(
                                imageVector = Icons.Filled.FormatBold,
                                contentDescription = "Make text bold",
                                tint = if (isBoldActivated.value) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onPrimary,
                            )
                        }
                        IconButton(
                            onClick = {
                                richTextState.value.toggleSpanStyle(
                                    SpanStyle(
                                        textDecoration = TextDecoration.Underline
                                    )
                                )
                                isUnderlineActivated.value = !isUnderlineActivated.value
                            },
                            modifier = Modifier
                                .clip(
                                    if (isUnderlineActivated.value) CircleShape else MaterialTheme.shapes.medium.copy(
                                        all = CornerSize(0.dp)
                                    )
                                )
                                .background(if (isUnderlineActivated.value) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primaryVariant)
                                .border(
                                    width = 0.dp,
                                    color = MaterialTheme.colors.primaryVariant,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.FormatUnderlined,
                                contentDescription = "Make text underline",
                                tint = if (isUnderlineActivated.value) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onPrimary,
                            )
                        }
                        IconButton(
                            onClick = {
                                richTextState.value.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                                isItalicActivated.value = !isItalicActivated.value
                            },
                            modifier = Modifier
                                .clip(
                                    if (isItalicActivated.value) CircleShape else MaterialTheme.shapes.medium.copy(
                                        all = CornerSize(0.dp)
                                    )
                                )
                                .background(if (isItalicActivated.value) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primaryVariant)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colors.primaryVariant,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.FormatItalic,
                                contentDescription = "Make text Italic",
                                tint = if (isItalicActivated.value) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onPrimary,
                            )
                        }
                        IconButton(onClick = {
                            showFontSize.value = !showFontSize.value

                        }) {
                            Icon(
                                imageVector = Icons.Filled.FormatSize,
                                contentDescription = "Change text format",
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                        IconButton(
                            onClick = {
                                richTextState.value.toggleOrderedList()
                                isOrderedListActivated.value = !isOrderedListActivated.value
                                isUnOrderedListActivated.value = false
                            },
                            modifier = Modifier
                                .clip(
                                    if (isOrderedListActivated.value) CircleShape else MaterialTheme.shapes.medium.copy(
                                        all = CornerSize(0.dp)
                                    )
                                )
                                .background(if (isOrderedListActivated.value) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primaryVariant)
                                .border(
                                    width = 0.dp,
                                    color = MaterialTheme.colors.primaryVariant,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.FormatListNumbered,
                                contentDescription = "Change text format",
                                tint = if (isOrderedListActivated.value) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onPrimary,
                            )
                        }
                        IconButton(
                            onClick = {
                                richTextState.value.toggleUnorderedList()
                                isUnOrderedListActivated.value = !isUnOrderedListActivated.value
                                isOrderedListActivated.value = false
                            },
                            modifier = Modifier
                                .clip(
                                    if (isUnOrderedListActivated.value) CircleShape else MaterialTheme.shapes.medium.copy(
                                        all = CornerSize(0.dp)
                                    )
                                )
                                .background(if (isUnOrderedListActivated.value) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primaryVariant)
                                .border(
                                    width = 0.dp,
                                    color = MaterialTheme.colors.primaryVariant,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.FormatListBulleted,
                                contentDescription = "Change text format",
                                tint = if (isUnOrderedListActivated.value) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onPrimary,
                            )
                        }
                        IconButton(onClick = {
                            richTextState.value.toggleParagraphStyle(
                                ParagraphStyle(
                                    textAlign = TextAlign.Justify
                                )
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Filled.AlignHorizontalCenter,
                                contentDescription = "Change text format",
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                }
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

