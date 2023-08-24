package com.pzbdownloaders.scribble.main_screen.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0 ,
    val title: String ="",
    val content: String = "",
    val timeStamp: Long = 0,
    val color: Int = 0,
)