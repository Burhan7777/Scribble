package com.pzbapps.squiggly.edit_note_feature.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pzbapps.squiggly.common.domain.utils.GetResult

class ArchiveNoteUseCase {

    fun archiveNote(noteId: String, map: HashMap<String, Any>): MutableLiveData<GetResult> {
        val fireStore = Firebase.firestore
        val getResult = MutableLiveData<GetResult>()
        fireStore.collection("Notes").document(noteId).update(map).addOnSuccessListener {
            getResult.value = GetResult.Success

        }.addOnFailureListener {
            getResult.value = GetResult.Failure(it.message)
        }
        return getResult
    }
}
