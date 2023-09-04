package com.pzbdownloaders.scribble.add_note_feature.domain.usecase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddNotebookUseCase {

    fun addNoteBook(notebook: String) {
        val fireStore = Firebase.firestore
        val docRef = fireStore.collection("notebook").document()
        fireStore.collection("Notebooks").document(docRef.id)
            .set(
                mapOf(
                    "notebook" to notebook,
                    "notebook_id" to docRef.id,
                    "user_id" to FirebaseAuth.getInstance().currentUser?.uid
                )
            ).addOnSuccessListener {

            }.addOnFailureListener {

            }

    }
}