package com.pzbdownloaders.scribble.common.data.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dummyTable")
data class DummyTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String = "",
    val timeModified: Long = 0,
    val timeStamp: Long = 0,
    val something:Long = 0,
    val something1:Long = 0
)
