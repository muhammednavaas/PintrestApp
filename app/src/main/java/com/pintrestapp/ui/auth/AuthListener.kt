package com.pintrestapp.ui.auth

import androidx.lifecycle.LiveData
import com.pintrestapp.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)

}