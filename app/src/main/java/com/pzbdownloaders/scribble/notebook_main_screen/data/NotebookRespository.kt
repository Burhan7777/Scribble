package com.pzbdownloaders.scribble.notebook_main_screen.data

import com.pzbdownloaders.scribble.common.data.data_source.NoteDatabase
import javax.inject.Inject

class NotebookRepository @Inject constructor(private val notebookDatabase: NoteDatabase) {

    suspend fun deleteNotebook(name: String) {
        notebookDatabase.getDao().deleteNotebook(name)
    }

}