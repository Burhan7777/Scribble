package com.pzbdownloaders.scribble.common.data.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("SELECT * from notes")
    fun getAllNotes(): List<Note>

    @Delete
    fun deleteAllNotes(list: List<Note>)

    @Query("SELECT * from notes where id= :id")
    suspend fun getNoteById(id: Int): Note

    @Query("DELETE from notes where id= :id")
    suspend fun deleteNoteById(id: Int)

    @Update
    suspend fun updateNote(note: Note)
}