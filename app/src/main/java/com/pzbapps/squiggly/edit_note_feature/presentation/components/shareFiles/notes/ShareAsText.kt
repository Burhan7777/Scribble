package com.pzbapps.squiggly.edit_note_feature.presentation.components.shareFiles.notes

import android.content.Context
import android.content.Intent

fun sharePlainText(context: Context, title: String, content: String) {
    // Combine title and content into a single string
    val shareText = "$title\n\n$content"

    // Create a share intent
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain" // Mime type for plain text
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    // Start the share intent
    context.startActivity(Intent.createChooser(shareIntent, "Share Note"))
}
