package com.pzbdownloaders.scribble.main_screen.presentation.components

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
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel

@Composable
fun SortNotesAlertBox(
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    onDismiss: () -> Unit
) {
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
            Column() {
                Text(
                    text = "Select the order",
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.fontFamilyRegular,
                    color = MaterialTheme.colors.onPrimary
                )
                MainCardStructure(nameOfOrder = "Date created (Newest first)") {
                    viewModel.dateCreatedOldestFirst.value = true
                }
                Spacer(modifier = Modifier.height(10.dp))
                MainCardStructure(nameOfOrder = "Date created (Oldest first)") {
                    viewModel.dateCreatedOldestFirst.value = true
                }
                Spacer(modifier = Modifier.height(10.dp))
                MainCardStructure(nameOfOrder = "Date modified (Newest first)") {
                    viewModel.dateModifiedNewestFirst.value = true
                }
                Spacer(modifier = Modifier.height(10.dp))
                MainCardStructure(nameOfOrder = "Date modified (Oldest first)") {
                    viewModel.dataModifiedOldestFirst.value = true
                }

            }
        }, confirmButton = {
        }, dismissButton = {
        })
}

@Composable
fun MainCardStructure(nameOfOrder: String, onCardClick: () -> Unit) {
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
                onCardClick()
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
                text = nameOfOrder,
                fontSize = 15.sp,
                fontFamily = FontFamily.fontFamilyRegular,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
    }
}