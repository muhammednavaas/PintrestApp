package com.pintrestapp.data.network.responses

import com.pintrestapp.data.db.entities.User

data class AuthResponse (
    val isSuccessful:Boolean?,
    val message:String?,
    val user: User?
)
