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
    val listOfBulletPointNotes: ArrayList<String> = arrayListOf(),
    val timeStamp: Long = 0,
    val editTime:String = "",
    val color: Int = 0,
    val deletedNote: Boolean = false
)
