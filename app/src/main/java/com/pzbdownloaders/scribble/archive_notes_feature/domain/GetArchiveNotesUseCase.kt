package com.pzbdownloaders.scribble.archive_notes_feature.domain

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pzbdownloaders.scribble.add_note_feature.domain.AddNote
import com.pzbdownloaders.scribble.common.domain.utils.GetResult
import kotlinx.coroutines.tasks.await

class GetArchiveNotesUseCase {

    suspend fun getArchivedNotes(): Pair<MutableLiveData<GetResult>, SnapshotStateList<AddNote>> {
        var notes: AddNote?
        val listOfNote = mutableStateListOf<AddNote>()
        val getResult = MutableLiveData<GetResult>()
        val firebaseStore = Firebase.firestore
        val querySnapshot =
            firebaseStore.collection("Notes")
                .whereEqualTo("userId", FirebaseAuth.getInstance().currentUser?.uid)
                .whereEqualTo("archived", true).get()
                .addOnSuccessListener {
                    getResult.value = GetResult.Success

                }.addOnFailureListener {
                    getResult.value = GetResult.Failure(it.message)
                }.await()


        for (i in querySnapshot.documents) {
            notes = i.toObject(AddNote::class.java)
            listOfNote.add(notes!!)
            Log.i("notes", listOfNote[0].toString())
        }

        return Pair(getResult, listOfNote)
    }

}