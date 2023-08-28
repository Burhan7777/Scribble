package com.pzbdownloaders.scribble.add_note_feature.domain.usecase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pzbdownloaders.scribble.add_note_feature.domain.model.AddNote
import com.pzbdownloaders.scribble.common.domain.utils.GetResult

class AddNoteUseCase {

    fun addNote(addNote: AddNote): MutableLiveData<GetResult> {
        val getResult = MutableLiveData<GetResult>()
        var firebaseStore = Firebase.firestore
        /*  firebaseStore.collection("Notes").document()
              .set(addNote, SetOptions.merge()).addOnSuccessListener() {
                  getResult.value = GetResult.Success

              }.addOnFailureListener {
                  getResult.value = GetResult.Failure(it.message)
              }*/
        var docRef = firebaseStore.collection("Notes").document()
        addNote.noteId = docRef.id
        docRef.set(addNote, SetOptions.merge()).addOnSuccessListener {
            getResult.value = GetResult.Success
        }.addOnFailureListener {
            getResult.value = GetResult.Success
        }
        return getResult
    }
}