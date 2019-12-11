package com.pintrestapp.ui.profile

import androidx.lifecycle.ViewModel
import com.pintrestapp.data.repository.UserRespository

class ProfileViewModel(
    repository: UserRespository
) : ViewModel() {
    val user = repository.getUser()

}
