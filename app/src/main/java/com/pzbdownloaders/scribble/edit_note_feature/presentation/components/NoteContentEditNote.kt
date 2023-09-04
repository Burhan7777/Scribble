package com.pzbdownloaders.scribble.edit_note_feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pzbdownloaders.scribble.R
import com.pzbdownloaders.scribble.add_note_feature.domain.model.GetNoteBook
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel


@Composable
fun NoteContent(
    selectedNotebook: MutableState<String>,
    isExpanded: MutableState<Boolean>,
    viewModel: MainActivityViewModel,
    title: String,
    content: String,
    noteBook: String,
    onChangeTitle: (String) -> Unit,
    onChangeContent: (String) -> Unit
) {

    viewModel.getNoteBook()
    val listOfNoteBooks = viewModel.getNoteBooks.observeAsState().value


    var notebooks: ArrayList<String> = ArrayList()

    for (i in listOfNoteBooks?.indices ?: arrayListOf<GetNoteBook>().indices) {
        notebooks.add(listOfNoteBooks!![i]?.notebook ?: GetNoteBook().notebook)
    }



    Row(
        horizontalArrangement = Arrangement.spacedBy(0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (noteBook.isNotEmpty()) {
            Text(
                text = if (selectedNotebook.value.isEmpty()) "Notebook:$noteBook" else "New Notebook selected: ${selectedNotebook.value}",
                color = MaterialTheme.colors.onPrimary,
                fontFamily = FontFamily.fontFamilyRegular,
                fontStyle = FontStyle.Italic,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 15.dp)
            )
        } else {
            Text(
                text = if (selectedNotebook.value.isEmpty()) "Add to Notebook" else "New Notebook selected: ${selectedNotebook.value}",
                color = MaterialTheme.colors.onPrimary,
                fontFamily = FontFamily.fontFamilyRegular,
                fontStyle = FontStyle.Italic,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 15.dp)
            )
        }

        /*     TextField(
                modifier = Modifier.wrapContentWidth(),
                 value = "Notebook:$noteBook",
                 onValueChange = { },
                 placeholder = {
                     Text(
                         text = "Notebook",
                         fontSize = 15.sp,
                         fontFamily = FontFamily.fontFamilyRegular,
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
                 textStyle = TextStyle(
                     fontFamily = FontFamily.fontFamilyRegular,
                     fontSize = 15.sp,
                     fontStyle = FontStyle.Italic
                 ),
                 readOnly = true
             )*/
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = "Arrow DropDown",
            modifier = Modifier.clickable {
                isExpanded.value = true
            })
        if (isExpanded.value) {
            Menu(isExpanded = isExpanded, notebooks, selectedNotebook)
        }
    }

    TextField(
        value = title,
        onValueChange = { onChangeTitle(it) },
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
        value = content,
        onValueChange = { onChangeContent(it) },
        placeholder = {
            Text(
                text = "Note",
                fontSize = 30.sp,
                fontFamily = FontFamily.fontFamilyLight,
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


@Composable
fun Menu(
    isExpanded: MutableState<Boolean>,
    notebooks: ArrayList<String>,
    selectedNotebook: MutableState<String>
) {
    DropdownMenu(
        offset = DpOffset.Zero,
        modifier = Modifier
            .width(200.dp)
            .background(MaterialTheme.colors.primaryVariant),
        expanded = isExpanded.value,
        onDismissRequest = { isExpanded.value = false }
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
                    selectedNotebook.value = item
                    isExpanded.value = false
                },
            )
        }
    }
}
