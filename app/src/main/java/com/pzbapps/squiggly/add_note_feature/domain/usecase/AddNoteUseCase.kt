package com.pzbapps.squiggly.add_note_feature.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pzbapps.squiggly.add_note_feature.domain.model.AddNote
import com.pzbapps.squiggly.common.domain.utils.GetResult

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