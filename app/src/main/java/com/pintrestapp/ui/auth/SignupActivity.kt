package com.pintrestapp.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pintrestapp.R
import com.pintrestapp.data.db.entities.User
import com.pintrestapp.databinding.ActivitySignupBinding
import com.pintrestapp.home.HomeActivity
import com.pintrestapp.util.hide
import com.pintrestapp.util.show
import com.pintrestapp.util.snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.progressbar
import kotlinx.android.synthetic.main.quotes_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class SignupActivity : AppCompatActivity(), AuthListener, KodeinAware {
    override val kodein by kodein()

    private val factory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        changeStatusBarColor()
        val binding: ActivitySignupBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_signup)
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)

                    overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
                    finish()
                }
            }
        })
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.register_bk_color)
        }
    }

    override fun onStarted() {
        progressbar.show()


    }

    override fun onSuccess(user: User) {
        progressbar.hide()


    }

    override fun onFailure(message: String) {
        progressbar.hide()
        snack_layout.snackbar(message)

    }

}
