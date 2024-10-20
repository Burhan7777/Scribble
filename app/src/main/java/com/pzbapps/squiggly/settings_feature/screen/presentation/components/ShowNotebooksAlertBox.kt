package com.pzbapps.squiggly.settings_feature.screen.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pzbapps.squiggly.common.presentation.FontFamily
import com.pzbapps.squiggly.common.presentation.MainActivity
import com.pzbapps.squiggly.common.presentation.MainActivityViewModel

@Composable
fun ShowNotebooksAlertBox(
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    onDismiss: () -> Unit
) {
    viewModel.getAllNotebooks()
    val listOfNotebooks = viewModel.listOfNoteBooks


    androidx.compose.material3.AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = androidx.compose.material.MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(15.dp),
            topEnd = CornerSize(15.dp),
            bottomStart = CornerSize(15.dp),
            bottomEnd = CornerSize(15.dp),
        ),
        containerColor = androidx.compose.material.MaterialTheme.colors.primaryVariant,
        title = {
            Column(modifier = Modifier.height(250.dp)) {
                Text(
                    text = "Select notebook to lock",
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.fontFamilyRegular,
                    color = MaterialTheme.colors.onPrimary
                )
                LazyColumn() {
                    items(listOfNotebooks.toMutableStateList()) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clip(CircleShape)
                                .border(
                                    1.dp,
                                    MaterialTheme.colors.onPrimary,
                                    shape = CircleShape
                                )
                                .clickable {
                                    viewModel.getNotebookByName(item.name)
                                    viewModel.getNoteBookByName.observe(activity) {
                                        val notebook = it.copy(lockedOrNote = true)
                                        viewModel.updateNotebook(notebook)
                                    }
                                },
                            shape = MaterialTheme.shapes.medium.copy(
                                topStart = CornerSize(15.dp),
                                topEnd = CornerSize(15.dp),
                                bottomStart = CornerSize(15.dp),
                                bottomEnd = CornerSize(15.dp),
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colors.primary,
                                contentColor = MaterialTheme.colors.onPrimary
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(start = 20.dp),
                            ) {
                                Text(
                                    text = item.name,
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily.fontFamilyRegular,
                                    color = MaterialTheme.colors.onPrimary,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }, confirmButton = {
        }, dismissButton = {
        })
}