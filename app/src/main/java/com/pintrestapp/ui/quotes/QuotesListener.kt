package com.pintrestapp.ui.quotes

import com.pintrestapp.data.db.entities.Quotes

interface QuotesListener {
    fun onStarted(message: String)
    fun onSuccess(it: List<Quotes>)
    fun onFailure(message: String)
}