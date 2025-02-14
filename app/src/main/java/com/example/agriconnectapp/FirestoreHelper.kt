package com.example.agriconnectapp

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreHelper {
    private val db = FirebaseFirestore.getInstance()

    fun saveUserData(userId: String, name: String, email: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val user = hashMapOf(
            "name" to name,
            "email" to email
        )

        db.collection("users").document(userId)
            .set(user)
            .addOnSuccessListener {
                Log.d("FirestoreHelper", "User data saved successfully")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreHelper", "Error saving user data", e)
                onFailure(e)
            }
    }

    fun getUserData(userId: String, onSuccess: (Map<String, Any>?) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    onSuccess(document.data)
                } else {
                    onSuccess(null)
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreHelper", "Error fetching user data", e)
                onFailure(e)
            }
    }
}
