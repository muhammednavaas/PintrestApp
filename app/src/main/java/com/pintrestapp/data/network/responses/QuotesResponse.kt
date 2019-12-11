package com.pintrestapp.data.network.responses

import com.pintrestapp.data.db.entities.Quotes

data class QuotesResponse(
    val isSuccessful: Boolean,
    val message:String?,
    val quotes: List<Quotes>
)