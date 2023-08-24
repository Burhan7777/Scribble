package com.pzbdownloaders.scribble.edit_note_feature.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.sp
import com.pzbdownloaders.scribble.R


@Composable
fun NoteContent(
    title: String,
    content: String,
    onChangeTitle: (String) -> Unit,
    onChangeContent: (String) -> Unit
) {

    val fontFamilyLight = Font(R.font.lufgalight).toFontFamily()
    val fontFamilyExtraLight = Font(R.font.lufgaextralight).toFontFamily()
    val fontFamilyBlack = Font(R.font.lufgablack).toFontFamily()
    val fontFamilyRegular = Font(R.font.lufgaregular).toFontFamily()

    TextField(
        value = title,
        onValueChange = { onChangeTitle(it) },
        placeholder = {
            Text(
                text = "Title",
                fontSize = 30.sp,
                fontFamily = fontFamilyExtraLight,
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
        textStyle = TextStyle(fontFamily = fontFamilyLight, fontSize = 25.sp)
    )

    TextField(
        value = content,
        onValueChange = { onChangeContent(it) },
        placeholder = {
            Text(
                text = "Note",
                fontSize = 30.sp,
                fontFamily = fontFamilyExtraLight,
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
        textStyle = TextStyle(fontFamily = fontFamilyLight, fontSize = 25.sp)
    )
}




