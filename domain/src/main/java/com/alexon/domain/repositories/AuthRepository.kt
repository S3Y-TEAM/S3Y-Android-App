package com.alexon.domain.repositories

interface AuthRepository {

    suspend fun login() : LoginResponse

    suspend fun register() {

    }
}

