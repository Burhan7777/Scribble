package com.pzbapps.squiggly.edit_note_feature.presentation.components.shareFiles.bulletpoints

import android.content.Context
import android.content.Intent

fun shareBulletPointsAsText(context: Context, title: String, bulletPoints: List<String>) {
    // Create a formatted string with bullet points
    val formattedText = StringBuilder()
    formattedText.append("$title\n\n") // Add title with spacing

    bulletPoints.forEach { point ->
        formattedText.append("• $point\n") // Prepend bullet symbol to each point
    }

    // Create a share intent
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, formattedText.toString()) // Set the text to share
    }

    // Launch the share intent
    context.startActivity(Intent.createChooser(shareIntent, "Share bullet points via"))
}
