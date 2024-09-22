package com.pzbdownloaders.scribble.main_screen.data.repository

import androidx.lifecycle.LiveData
import com.pzbdownloaders.scribble.common.data.data_source.NoteDatabase
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDatabase: NoteDatabase) {
     suspend fun getAllNotes(): List<Note> {
        return noteDatabase.getDao().getAllNotes()
    }

    suspend fun getNotesByNoteBook(notebook: String): List<Note> {
        return noteDatabase.getDao().getNoteByNotebook(notebook)
    }

    suspend fun deleteAllNotes(list: List<Note>) {
        noteDatabase.getDao().deleteAllNotes(list)
    }
}