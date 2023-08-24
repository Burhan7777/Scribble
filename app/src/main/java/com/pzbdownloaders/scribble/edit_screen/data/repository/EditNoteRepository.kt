package com.pzbdownloaders.scribble.edit_screen.data.repository

import com.pzbdownloaders.scribble.common.data.data_source.NoteDatabase
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import javax.inject.Inject

class EditNoteRepository @Inject constructor(private val noteDatabase: NoteDatabase) {

    suspend fun getNotesById(id: Int): Note {
        return noteDatabase.getDao().getNoteById(id)
    }

    suspend fun updateNote(note: Note) {
        noteDatabase.getDao().updateNote(note)
    }

    suspend fun deleteNote(id: Int) {
        noteDatabase.getDao().deleteNoteById(id)
    }
}