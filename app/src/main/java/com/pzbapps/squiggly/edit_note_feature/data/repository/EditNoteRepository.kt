package com.pzbapps.squiggly.edit_note_feature.data.repository

import com.pzbapps.squiggly.common.data.data_source.NoteDatabase
import com.pzbapps.squiggly.main_screen.domain.model.Note
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

    suspend fun deleteTrashNote(deletedNote: Boolean) {
        noteDatabase.getDao().deleteNoteFromTrash(deletedNote)
    }

    suspend fun moveToTrashById(deletedNote: Boolean, timePutInTrash: Long, id: Int) {
        noteDatabase.getDao().moveToTrashById(deletedNote, timePutInTrash, id)
    }

    suspend fun moveToArchive(archive: Boolean, id: Int) {
        noteDatabase.getDao().moveToArchive(archive, id)
    }

    suspend fun lockOrUnlockNote(lockOrUnlock: Boolean, id: Int) {
        noteDatabase.getDao().lockOrUnlockNote(lockOrUnlock, id)
    }

    suspend fun pinOrUnpinNote(pinOrUnpin: Boolean, id: Int) {
        noteDatabase.getDao().pinOrUnpinNote(pinOrUnpin, id)
    }
}