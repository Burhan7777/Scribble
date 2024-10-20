package com.pzbapps.squiggly.main_screen.presentation.components

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pzbapps.squiggly.common.domain.utils.Constant
import com.pzbapps.squiggly.common.presentation.FontFamily
import com.pzbapps.squiggly.common.presentation.MainActivity
import com.pzbapps.squiggly.common.presentation.MainActivityViewModel


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
                MainCardStructure(nameOfOrder = "Date created", onDismiss) {
                    val editor: SharedPreferences.Editor =
                        activity.getSharedPreferences(Constant.SORT_ORDER, MODE_PRIVATE).edit()
                    editor.putString(Constant.SORT_ORDER_KEY, Constant.SORT_ORDER_VALUE_1)
                    editor.apply()
                }
                Spacer(modifier = Modifier.height(10.dp))
                MainCardStructure(nameOfOrder = "Date modified", onDismiss) {
                    val editor: SharedPreferences.Editor =
                        activity.getSharedPreferences(Constant.SORT_ORDER, MODE_PRIVATE).edit()
                    editor.putString(Constant.SORT_ORDER_KEY, Constant.SORT_ORDER_VALUE_2)
                    editor.apply()
                }

            }
        }, confirmButton = {
        }, dismissButton = {
        })
}

@Composable
fun MainCardStructure(nameOfOrder: String, onCardClick: () -> Unit, onDismiss: () -> Unit) {
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
                onDismiss()
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