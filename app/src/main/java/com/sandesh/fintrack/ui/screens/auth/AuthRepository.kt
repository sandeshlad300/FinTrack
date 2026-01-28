package com.sandesh.fintrack.ui.screens.auth



interface AuthRepository {

    suspend fun login(email: String, password: String): Result<String>

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<Unit>

    suspend fun sendPasswordReset(email: String): Result<Unit>
}
