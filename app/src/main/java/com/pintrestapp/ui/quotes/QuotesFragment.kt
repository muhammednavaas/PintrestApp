package com.pintrestapp.ui.quotes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pintrestapp.R
import com.pintrestapp.data.db.entities.Quotes
import com.pintrestapp.util.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.quotes_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: QuotesViewModelFactory by instance()


    private lateinit var viewModel: QuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(QuotesViewModel::class.java)

        bindUI()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun bindUI() = Coroutines.main {
        progressbar.show()
        try {

            viewModel.quotes.await().observe(this, Observer {
                progressbar.hide()
                initRecyClerView(it.toQuoteItem())


            })
        } catch (e: ApiException) {
            progressbar.hide()
            layout.snackbar(e.message!!)

        } catch (e: NoInternetException) {
            progressbar.hide()
            layout.snackbar(e.message!!)
        }


    }

    private fun initRecyClerView(quoteItem: List<QuoteItem>) {
        val mAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(quoteItem)
        }
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }


}


private fun List<Quotes>.toQuoteItem(): List<QuoteItem> {
    return this.map {
        QuoteItem(it)
    }


}


