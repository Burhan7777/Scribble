package com.pzbdownloaders.scribble.common.data.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notebook")
data class NoteBook(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name:String = "",
    var lockedOrNote:Boolean = false
)
