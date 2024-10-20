package com.pzbdownloaders.scribble.edit_note_feature.presentation.components.shareFiles.checkboxes

import android.content.Context
import android.content.Intent

fun exportToTextCheckBoxes(
    context: Context,
    title: String,
    checkboxStates: ArrayList<Boolean>,
    checkboxTexts: List<String>
) {
    // Build the text output
    val stringBuilder = StringBuilder()
    stringBuilder.append("$title\n\n")

    for (i in checkboxTexts.indices) {
        val checkboxSymbol = if (checkboxStates[i]) "[X]" else "[ ]"
        stringBuilder.append("$checkboxSymbol ${checkboxTexts[i]}\n")
    }

    // Convert StringBuilder to a plain text string
    val outputText = stringBuilder.toString()

    // Share the text using an Intent
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, outputText)
        type = "text/plain"
    }

    // Start the share activity
    val shareIntent = Intent.createChooser(sendIntent, "Share checklist via")
    context.startActivity(shareIntent)
}
