package com.pzbapps.squiggly.settings_feature.screen.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pzbapps.squiggly.R
import com.pzbapps.squiggly.common.presentation.FontFamily

@Composable
fun PrivacyPolicy() {
    LazyColumn {
        item {
            Text(
                text = stringResource(id = R.string.privacyPolicy),
                fontFamily = FontFamily.fontFamilyRegular,
                fontSize = 15.sp,
                modifier = Modifier.padding(10.dp),
                color = MaterialTheme.colors.onPrimary
            )
        }
    }

}