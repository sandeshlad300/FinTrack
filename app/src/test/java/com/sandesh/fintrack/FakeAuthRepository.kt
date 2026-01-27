package com.sandesh.fintrack

import com.sandesh.fintrack.ui.screens.auth.AuthRepository


class FakeAuthRepository(
    private val success: Boolean = true
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<String> =
        if (success) Result.success("Sandesh")
        else Result.failure(Exception("Invalid credentials"))

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<Unit> =
        if (success) Result.success(Unit)
        else Result.failure(Exception("Registration failed"))

    override suspend fun sendPasswordReset(email: String): Result<Unit> =
        Result.success(Unit)
}
