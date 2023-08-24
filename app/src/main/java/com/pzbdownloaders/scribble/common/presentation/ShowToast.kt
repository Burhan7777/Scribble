package com.pzbdownloaders.scribble.common.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShowShortToast(msg: String) {
    Toast.makeText(LocalContext.current, msg, Toast.LENGTH_SHORT).show()
}

@Composable
fun ShowLongToast(msg: String) {
    Toast.makeText(LocalContext.current, msg, Toast.LENGTH_LONG).show()
}