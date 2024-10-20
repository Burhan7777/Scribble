package com.pzbapps.squiggly.settings_feature.screen.presentation.components

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pzbapps.squiggly.common.domain.utils.Constant
import com.pzbapps.squiggly.common.presentation.FontFamily


@Composable
fun ChangeThemeDialogBox(showChangeThemeDialogBox: MutableState<Boolean>, onDIsmiss: () -> Unit) {
    val context = LocalContext.current
    androidx.compose.material3.AlertDialog(
        onDismissRequest = { onDIsmiss() },
        shape = androidx.compose.material.MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(15.dp),
            topEnd = CornerSize(15.dp),
            bottomStart = CornerSize(15.dp),
            bottomEnd = CornerSize(15.dp),
        ),
        containerColor = androidx.compose.material.MaterialTheme.colors.primaryVariant,
        title = {
            Column {
                Text(
                    text = "Select theme",
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.fontFamilyRegular,
                    color = MaterialTheme.colors.onPrimary
                )

                SelectThemeCardView(name = "System default") {
                    val editor: SharedPreferences.Editor =
                        context.getSharedPreferences(Constant.CHANGE_THEME, MODE_PRIVATE).edit()
                    editor.putString(Constant.THEME_KEY, Constant.SYSTEM_DEFAULT)
                    editor.apply()
                    showChangeThemeDialogBox.value = false
                }
                SelectThemeCardView(name = "Dark theme") {
                    val editor: SharedPreferences.Editor =
                        context.getSharedPreferences(Constant.CHANGE_THEME, MODE_PRIVATE).edit()
                    editor.putString(Constant.THEME_KEY, Constant.DARK_THEME)
                    editor.apply()
                    showChangeThemeDialogBox.value = false
                }
                SelectThemeCardView(name = "Light theme") {
                    val editor: SharedPreferences.Editor =
                        context.getSharedPreferences(Constant.CHANGE_THEME, MODE_PRIVATE).edit()
                    editor.putString(Constant.THEME_KEY, Constant.LIGHT_THEME)
                    editor.apply()
                    showChangeThemeDialogBox.value = false
                }
            }
        }, confirmButton = {
//            androidx.compose.material.Button(
//                onClick = {
//
//                },
//                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
//                    contentColor = androidx.compose.material.MaterialTheme.colors.primary
//                ),
//                shape = androidx.compose.material.MaterialTheme.shapes.medium.copy(
//                    topStart = CornerSize(15.dp),
//                    topEnd = CornerSize(15.dp),
//                    bottomStart = CornerSize(15.dp),
//                    bottomEnd = CornerSize(15.dp),
//                )
//            ) {
//                androidx.compose.material.Text(
//                    text = "Change Password",
//                    fontFamily = FontFamily.fontFamilyRegular
//                )
//            }
        },
        dismissButton = {
            androidx.compose.material.OutlinedButton(
                onClick = {
                    onDIsmiss()
                },
                shape = androidx.compose.material.MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(15.dp),
                    topEnd = CornerSize(15.dp),
                    bottomStart = CornerSize(15.dp),
                    bottomEnd = CornerSize(15.dp),
                ),
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colors.onPrimary
                ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = androidx.compose.material.MaterialTheme.colors.primaryVariant,
                    contentColor = androidx.compose.material.MaterialTheme.colors.onPrimary
                ),
            ) {
                androidx.compose.material.Text(
                    text = "Cancel",
                    fontFamily = FontFamily.fontFamilyRegular
                )
            }
        })
}

@Composable
fun SelectThemeCardView(name: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(10.dp)
            .clickable {
                onClick()
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
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = name,
                fontSize = 18.sp,
                fontFamily = FontFamily.fontFamilyRegular,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 10.dp)
            )
        }
    }

}