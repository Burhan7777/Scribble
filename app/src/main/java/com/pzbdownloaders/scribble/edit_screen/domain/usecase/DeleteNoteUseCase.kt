package com.pzbdownloaders.scribble.edit_screen.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pzbdownloaders.scribble.common.domain.utils.GetResult

class DeleteNoteUseCase {

    fun deleteNote(noteId: String): MutableLiveData<GetResult> {
        var getResult: MutableLiveData<GetResult> = MutableLiveData()
        val fireStore = Firebase.firestore
        val docRef = fireStore.collection("Notes").document(noteId)
        docRef.delete().addOnSuccessListener {
            getResult.value = GetResult.Success
        }.addOnFailureListener {
            getResult.value = GetResult.Failure(it.message)
        }
        return getResult
    }
}