package com.pintrestapp

import android.app.Application
import com.pintrestapp.data.db.AppDataBase
import com.pintrestapp.data.network.MyApi
import com.pintrestapp.data.network.NetworkConnectionInterseptor
import com.pintrestapp.data.preferences.PreferenceProvider
import com.pintrestapp.data.repository.QuotesRepository
import com.pintrestapp.data.repository.UserRespository
import com.pintrestapp.ui.auth.AuthViewModelFactory
import com.pintrestapp.ui.profile.ProfileViewModelFactory
import com.pintrestapp.ui.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MyApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))
        bind() from singleton { NetworkConnectionInterseptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDataBase(instance()) }
        bind()from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRespository(instance(), instance()) }
        bind() from singleton { QuotesRepository(instance(), instance(),instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { QuotesViewModelFactory(instance()) }


    }


}