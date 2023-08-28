package com.pzbdownloaders.scribble.edit_note_feature.domain.usecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pzbdownloaders.scribble.add_note_feature.domain.model.AddNote
import com.pzbdownloaders.scribble.common.domain.utils.GetResult
import kotlinx.coroutines.tasks.await

class EditNoteUseCase {

    suspend fun getNoteToEdit(noteId: String): Pair<GetResult?, AddNote?> {
        var note: AddNote?
        var getResult: GetResult? = null
        var firebaseStore = Firebase.firestore
        var docRef = firebaseStore.collection("Notes").document(noteId)
        var documentSnapShot = docRef.get().addOnSuccessListener {

            getResult = GetResult.Success
        }.addOnFailureListener {
            getResult = GetResult.Failure(it.message)
        }.await()
        note = documentSnapShot.toObject(AddNote::class.java)
        Log.i("note123", note.toString())
        return Pair(getResult, note)
    }
}