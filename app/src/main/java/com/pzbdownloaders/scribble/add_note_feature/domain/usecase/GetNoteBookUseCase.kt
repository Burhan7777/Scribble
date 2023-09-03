package com.pzbdownloaders.scribble.add_note_feature.domain.usecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pzbdownloaders.scribble.add_note_feature.domain.model.GetNoteBook
import kotlinx.coroutines.tasks.await

class GetNoteBookUseCase {

    suspend fun getNoteBook(): ArrayList<GetNoteBook?> {
        var listOfNoteBooks = ArrayList<GetNoteBook?>()
        val fireStore = Firebase.firestore
        val querySnapshot = fireStore.collection("Notebooks").get().addOnSuccessListener {

        }.addOnFailureListener {

        }.await()
        for (i in querySnapshot.documents) {
            val notebook = i.toObject(GetNoteBook::class.java)
            Log.i("notebookusecase1", notebook.toString())

            listOfNoteBooks.add(notebook)
        }
        Log.i("notebookusecase", listOfNoteBooks.size.toString())
        return listOfNoteBooks
    }
}