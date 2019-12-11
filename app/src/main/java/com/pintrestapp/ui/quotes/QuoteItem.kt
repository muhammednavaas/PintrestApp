package com.pintrestapp.ui.quotes

import com.pintrestapp.R
import com.pintrestapp.data.db.entities.Quotes
import com.pintrestapp.databinding.ItemQuotesBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(
    private val quote: Quotes
) : BindableItem<ItemQuotesBinding>() {
    override fun getLayout() =
        R.layout.item_quotes


    override fun bind(viewBinding: ItemQuotesBinding, position: Int) {
        viewBinding.setQuote(quote)
    }


}