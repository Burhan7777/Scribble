package com.pzbapps.squiggly.main_screen.data.repository

import com.pzbapps.squiggly.common.data.data_source.NoteDatabase
import com.pzbapps.squiggly.main_screen.domain.model.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDatabase: NoteDatabase) {
    suspend fun getAllNotes(): List<Note> {
        return noteDatabase.getDao().getAllNotes()
    }

    fun getAllNotesByDateModified(): List<Note> {
        return noteDatabase.getDao().getAllNotesByDateModified()
    }

    suspend fun getNotesByNoteBook(notebook: String): List<Note> {
        return noteDatabase.getDao().getNoteByNotebook(notebook)
    }

    suspend fun deleteAllNotes(list: List<Note>) {
        noteDatabase.getDao().deleteAllNotes(list)
    }
}