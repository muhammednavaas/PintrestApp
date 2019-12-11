package com.pintrestapp.ui.auth


import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.pintrestapp.data.repository.UserRespository
import com.pintrestapp.util.ApiException
import com.pintrestapp.util.Coroutines
import com.pintrestapp.util.NoInternetException

class AuthViewModel(
    private val repository: UserRespository
) : ViewModel() {
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var mobile: String? = null
    var authListener: AuthListener? = null
    fun getLoggedInUser() = repository.getUser()

    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid Email and Password")
            return
        }
        Coroutines.main {
            try {
                val authResponse = repository.userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }


        }


    }

    fun onLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignUp(view: View) {
        Intent(view.context, SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignUpButtonClick(view: View) {
        authListener?.onStarted()
        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Please enter name")
            return
        }
        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Please enter email")
            return
        }

        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Please enter password")
            return
        }
        Coroutines.main {
            try {
                val authResponse = repository.userSignUp(name!!, email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }


        }
    }

}
