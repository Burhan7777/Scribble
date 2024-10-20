package com.pzbapps.squiggly.notebook_main_screen.data

import com.pzbapps.squiggly.common.data.data_source.NoteDatabase
import javax.inject.Inject

class NotebookRepository @Inject constructor(private val notebookDatabase: NoteDatabase) {

    suspend fun deleteNotebook(name: String) {
        notebookDatabase.getDao().deleteNotebook(name)
    }

}