package com.pintrestapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pintrestapp.data.db.AppDataBase
import com.pintrestapp.data.db.entities.Quotes
import com.pintrestapp.data.network.MyApi
import com.pintrestapp.data.network.SafeApiRequest
import com.pintrestapp.data.preferences.PreferenceProvider
import com.pintrestapp.ui.quotes.QuotesListener
import com.pintrestapp.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private val MINIMUM_INTREVAL = 6

class QuotesRepository(
    private val api: MyApi,
    private val db: AppDataBase,
    private val preferences: PreferenceProvider,
    val quotesListener: QuotesListener? = null

) : SafeApiRequest() {
    private val quotes = MutableLiveData<List<Quotes>>()

    init {
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    suspend fun getQuotes(): LiveData<List<Quotes>> {
        return withContext(Dispatchers.IO)
        {
            fetchQuotes()
            db.getQuotesDao().getQuotes()
        }

    }

    private suspend fun fetchQuotes() {

            val lastSavedAt = preferences.getLastSavedAt()
            if (lastSavedAt == null || isFetchedNeeded(LocalDateTime.parse(lastSavedAt))) {
                val quotesResponse = apiRequest {
                    api.getQuotes()
                }

                quotes.postValue(quotesResponse.quotes)
            }

    }

    private fun isFetchedNeeded(savedAt: LocalDateTime): Boolean {
        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTREVAL
    }

    private fun saveQuotes(quotes: List<Quotes>) {
        Coroutines.io {
            preferences.savelastSavedAt(LocalDateTime.now().toString())
            db.getQuotesDao().saveallQuotes(quotes)
        }
    }
}