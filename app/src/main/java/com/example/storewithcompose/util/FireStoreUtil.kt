package com.example.storewithcompose.util

// FirestoreUtil.kt

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.hilt.InstallIn
import kotlinx.coroutines.tasks.await

object FirestoreUtil {

    private val db = FirebaseFirestore.getInstance()

    suspend fun deleteFromFirebase(collection: CollectionReference, documentId: String) {
        try {
            val documentRef: DocumentReference = collection.document(documentId)
            documentRef.delete().await()
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }


}

