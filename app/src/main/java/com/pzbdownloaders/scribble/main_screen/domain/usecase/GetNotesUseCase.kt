package com.pzbdownloaders.scribble.main_screen.domain.usecase

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.pzbdownloaders.scribble.add_note_screen.domain.AddNote
import com.pzbdownloaders.scribble.common.domain.utils.GetResult
import com.pzbdownloaders.scribble.common.presentation.Constant
import com.pzbdownloaders.scribble.main_screen.data.repository.NoteRepository
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import com.pzbdownloaders.scribble.main_screen.domain.utils.NotesOrder
import com.pzbdownloaders.scribble.main_screen.domain.utils.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetNotesUseCase {

    suspend fun getNotes(): Pair<MutableLiveData<GetResult>, SnapshotStateList<AddNote>> {
        var notes: AddNote?
        val listOfNote = mutableStateListOf<AddNote>()
        val listOfNotes = SnapshotStateList<AddNote>()
        val getResult = MutableLiveData<GetResult>()
        val firebaseStore = Firebase.firestore
        val querySnapshot =
            firebaseStore.collection("Notes")
                .whereEqualTo("userId", FirebaseAuth.getInstance().currentUser?.uid).get()
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

        //    listOfNotes.add(listOfNote)
//        Log.i("size", listOfNotes.value?.get(0).toString())
        return Pair(getResult, listOfNote)
    }

}

