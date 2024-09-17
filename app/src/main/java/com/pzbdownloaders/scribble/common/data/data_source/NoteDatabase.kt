package com.pzbdownloaders.scribble.common.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.pzbdownloaders.scribble.common.data.Model.DummyTable
import com.pzbdownloaders.scribble.common.data.Model.NoteBook
import com.pzbdownloaders.scribble.common.data.data_source.Dao
import com.pzbdownloaders.scribble.common.domain.utils.ConverterBoolean
import com.pzbdownloaders.scribble.common.domain.utils.ConverterString
import com.pzbdownloaders.scribble.main_screen.domain.model.Note

@Database(entities = [Note::class, NoteBook::class, DummyTable::class], version = 19)
@TypeConverters(ConverterBoolean::class, ConverterString::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getDao(): Dao
}