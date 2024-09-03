package com.pzbdownloaders.scribble.common.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pzbdownloaders.scribble.common.data.Model.NoteBook
import com.pzbdownloaders.scribble.common.data.data_source.Dao
import com.pzbdownloaders.scribble.main_screen.domain.model.Note

@Database(entities = [Note::class, NoteBook::class], version = 4)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getDao(): Dao
}