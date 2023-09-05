package com.pzbdownloaders.scribble.notebook_main_screen.domain

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.pzbdownloaders.scribble.add_note_feature.domain.model.AddNote
import kotlinx.coroutines.tasks.await

class GetNotebookNotesUseCase {

    suspend fun getNotebookNote(notebook: String): ArrayList<AddNote?> {
        val listOfNotebookNotes = ArrayList<AddNote?>()
        val fireStore = Firebase.firestore
        val querySnapshot = fireStore.collection("Notes").whereEqualTo("label", notebook).get()
            .addOnSuccessListener {

            }.addOnFailureListener {

            }.await()
        for (i in querySnapshot.documents) {
            val notebookNote = i.toObject(AddNote::class.java)
            listOfNotebookNotes.add(notebookNote)
        }
        return listOfNotebookNotes
    }
}