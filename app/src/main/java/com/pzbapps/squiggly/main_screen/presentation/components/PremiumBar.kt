package com.pzbapps.squiggly.main_screen.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pzbapps.squiggly.common.presentation.FontFamily
import com.pzbapps.squiggly.common.presentation.MainActivity
import com.pzbapps.squiggly.main_screen.domain.usecase.getRemainingDaysFromFirebase


@Composable
fun ShowPremiumBar(activity: MainActivity) {
    var remainingDays = remember { mutableStateOf("") }
    var result = getRemainingDaysFromFirebase()
    result.observe(activity) {
        remainingDays.value = it
    }
    Box(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colors.onPrimary,
                        MaterialTheme.colors.onSecondary
                    )
                )
            )
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Text(
                text = "Buy app forever",
                fontFamily = FontFamily.fontFamilyRegular,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(start = 10.dp),
                color = MaterialTheme.colors.onSecondary
            )
            Text(
                text = "${remainingDays.value} days remaining",
                fontFamily = FontFamily.fontFamilyRegular,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(start = 10.dp),
                color = MaterialTheme.colors.onSecondary
            )
        }
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(15.dp),
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Buy App",
                fontFamily = FontFamily.fontFamilyBold,
                color = Color.Black
            )
        }
    }
}