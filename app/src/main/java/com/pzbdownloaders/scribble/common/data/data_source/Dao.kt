package com.pzbdownloaders.scribble.common.data.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.pzbdownloaders.scribble.common.data.Model.NoteBook
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface Dao {

    @Upsert
    suspend fun insertNote(note: Note): Long

    @Query("SELECT * from notes ORDER BY timeStamp DESC")
    fun getAllNotes(): List<Note>

    @Delete
    fun deleteAllNotes(list: List<Note>)

    @Query("SELECT * from notes where id= :id")
    suspend fun getNoteById(id: Int): Note

    @Query("DELETE from notes where id= :id")
    suspend fun deleteNoteById(id: Int)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM notes where notebook= :notebook ORDER BY timeStamp DESC")
    suspend fun getNoteByNotebook(notebook: String): List<Note>

    @Upsert
    suspend fun addNoteBook(notebook: NoteBook)

    @Update
    suspend fun updateNotebook(notebook: NoteBook)

    @Query(
        """
    UPDATE notes 
    SET deletedNote = :deletedNote, 
        timePutInTrash = :timePutInTrash 
    WHERE id = :id
"""
    )
    suspend fun moveToTrashById(deletedNote: Boolean, timePutInTrash: Long, id: Int)

    @Query("UPDATE notes set archive= :archive WHERE id= :id")
    suspend fun moveToArchive(archive: Boolean, id: Int)

    @Query("SELECT * FROM notebook where name= :name")
    suspend fun getNotebookByName(name: String): NoteBook

    @Query("DELETE FROM notebook where name= :notebook")
    suspend fun deleteNotebook(notebook: String)

    @Query("SELECT * from notebook")
    suspend fun getAllNoteBooks(): List<NoteBook>

    @Query("DELETE FROM notes where deletedNote = :deletedNote")
    suspend fun deleteNoteFromTrash(deletedNote: Boolean)

    @RawQuery
    fun checkpoint(supportSQLiteQuery: SupportSQLiteQuery): Int

    @Transaction
    suspend fun updateAndRefreshNote(note: Note): Note {
        updateNote(note)
        return getNoteById(note.id)
    }
}
