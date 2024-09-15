package com.pzbdownloaders.scribble.settings_feature.screen.data

import com.pzbdownloaders.scribble.common.data.Model.NoteBook
import com.pzbdownloaders.scribble.common.data.data_source.NoteDatabase
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val noteDatabase: NoteDatabase) {

    suspend fun updateNoteBook(noteBook: NoteBook) {
        noteDatabase.getDao().updateNotebook(noteBook)
    }

    suspend fun getNotebookByName(name: String): NoteBook {
        return noteDatabase.getDao().getNotebookByName(name)
    }
}