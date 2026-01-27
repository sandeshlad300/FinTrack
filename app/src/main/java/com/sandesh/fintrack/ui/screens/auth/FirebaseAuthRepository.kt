package com.sandesh.fintrack.ui.screens.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sandesh.fintrack.core.data.AppPreferences
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val appPreferences: AppPreferences
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Result<String> = try {

        auth.signInWithEmailAndPassword(email, password).await()
        val uid = auth.currentUser?.uid ?: return Result.failure(Exception("User not found"))

        val snapshot = firestore.collection("users")
            .document(uid)
            .get()
            .await()

        val name = snapshot.getString("name") ?: ""
        appPreferences.setUserName(name)

        Result.success(name)

    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<Unit> = try {

        auth.createUserWithEmailAndPassword(email, password).await()
        val uid = auth.currentUser?.uid ?: ""

        firestore.collection("users")
            .document(uid)
            .set(mapOf("name" to name))
            .await()

        Result.success(Unit)

    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun sendPasswordReset(email: String): Result<Unit> = try {
        auth.sendPasswordResetEmail(email).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}