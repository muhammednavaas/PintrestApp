package com.pintrestapp.data.repository

import com.pintrestapp.data.db.AppDataBase
import com.pintrestapp.data.db.entities.User
import com.pintrestapp.data.network.responses.AuthResponse
import com.pintrestapp.data.network.MyApi
import com.pintrestapp.data.network.SafeApiRequest

class UserRespository(
    private val api: MyApi,
    private val db: AppDataBase
) : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }

    }

    suspend fun userSignUp(name: String, email: String, password: String): AuthResponse {
        return apiRequest { api.userSignUp(name, email, password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}