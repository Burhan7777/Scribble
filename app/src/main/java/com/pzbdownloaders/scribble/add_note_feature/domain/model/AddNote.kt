package com.pzbdownloaders.scribble.add_note_feature.domain.model

import com.google.firebase.firestore.DocumentId

@kotlinx.serialization.Serializable
data class AddNote(
    var noteId: String = "",
    val title: String = "",
    val content: String = "",
    val userId: String = "",
    val timeStamp: Long = 0,
    val color: Int = 0,
    val isArchived: Boolean = false,
    val label: String = ""
)