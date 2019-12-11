@file:Suppress("UNCHECKED_CAST")

package com.pintrestapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pintrestapp.data.repository.UserRespository

class ProfileViewModelFactory(
    private val respository: UserRespository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(respository)as T
    }
}