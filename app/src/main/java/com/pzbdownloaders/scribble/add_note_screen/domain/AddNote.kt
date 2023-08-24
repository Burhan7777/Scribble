package com.pzbdownloaders.scribble.add_note_screen.domain

import com.google.firebase.firestore.DocumentId

data class AddNote(
    @DocumentId
    var noteId: String = "",
    val title: String = "",
    val content: String = "",
    val userId: String = "",
    val timeStamp: Long = 0,
    val color: Int = 0,
    val isArchived: Boolean = false,
    val label: String = ""
)