package com.pzbdownloaders.scribble.add_note_feature.data.repository

import com.pzbdownloaders.scribble.common.data.Model.NoteBook
import com.pzbdownloaders.scribble.common.data.data_source.NoteDatabase
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import javax.inject.Inject

class InsertNoteRepository @Inject constructor(private val noteDatabase: NoteDatabase) {

    suspend fun insertNote(note: Note) {
        noteDatabase.getDao().insertNote(note)
    }

    suspend fun addNoteBook(notebook: NoteBook) {
        noteDatabase.getDao().addNoteBook(notebook)
    }

    suspend fun getAllNoteBooks(): List<NoteBook> {
        return noteDatabase.getDao().getAllNoteBooks()
    }

    fun addNoteToCloud(note: Note) {

    }
}