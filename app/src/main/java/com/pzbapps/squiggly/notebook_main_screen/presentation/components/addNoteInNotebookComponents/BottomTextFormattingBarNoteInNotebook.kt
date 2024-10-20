package com.pzbapps.squiggly.notebook_main_screen.presentation.components.addNoteInNotebookComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.model.RichTextState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BottomTextFormattingBarNoteInNotebook(
    showFontSize: MutableState<Boolean>,
    fontSize: MutableState<String>,
    richTextState: MutableState<RichTextState>,
    isBoldActivated: MutableState<Boolean>,
    isUnderlineActivated: MutableState<Boolean>,
    isItalicActivated: MutableState<Boolean>,
    isOrderedListActivated: MutableState<Boolean>,
    isUnOrderedListActivated: MutableState<Boolean>,
) {
    Column(modifier = Modifier.imePadding()) {
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
                    Icon(
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
                    Icon(
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
                        textAlign = TextAlign.Left
                    )
                )
            }) {
                Icon(
                    imageVector = Icons.Filled.FormatAlignLeft,
                    contentDescription = "Change text format",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(onClick = {
                richTextState.value.toggleParagraphStyle(
                    ParagraphStyle(
                        textAlign = TextAlign.End
                    )
                )
            }) {
                Icon(
                    imageVector = Icons.Filled.FormatAlignRight,
                    contentDescription = "Change text format",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(onClick = {
                richTextState.value.toggleParagraphStyle(
                    ParagraphStyle(
                        textAlign = TextAlign.Center
                    )
                )
            }) {
                Icon(
                    imageVector = Icons.Filled.FormatAlignCenter,
                    contentDescription = "Change text format",
                    tint = MaterialTheme.colors.onPrimary
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
                    imageVector = Icons.Filled.FormatAlignJustify,
                    contentDescription = "Change text format",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}
