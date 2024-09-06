package com.pzbdownloaders.scribble.main_screen.domain.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val archive: Boolean = false,
    val notebook: String = "Not Categorized",
    val locked: Boolean = false,
    val listOfCheckedNotes: ArrayList<String> = arrayListOf(),
    val listOfCheckedBoxes: ArrayList<Boolean> = arrayListOf(),
    val timeStamp: Long = 0,
    val color: Int = 0,
)
