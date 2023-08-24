package com.pzbdownloaders.scribble.common.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pzbdownloaders.scribble.common.data.data_source.Dao
import com.pzbdownloaders.scribble.main_screen.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getDao(): Dao
}