package com.pintrestapp.ui.quotes

import androidx.lifecycle.ViewModel
import com.pintrestapp.data.repository.QuotesRepository
import com.pintrestapp.util.lazyDefferred

class QuotesViewModel(
    repository: QuotesRepository,

) : ViewModel() {
    val quotes by lazyDefferred {
        repository.getQuotes()
    }
}
