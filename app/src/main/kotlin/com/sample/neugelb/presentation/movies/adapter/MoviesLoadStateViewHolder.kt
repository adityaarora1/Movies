package com.sample.neugelb.presentation.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.sample.neugelb.R
import kotlinx.android.synthetic.main.item_list_footer.view.*

class MoviesLoadStateViewHolder(
    view: View,
    retry: () -> Unit
) : RecyclerView.ViewHolder(view) {

    init {
        itemView.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) itemView.errorMsg.text = loadState.error.localizedMessage
        itemView.progressBar.isVisible = loadState is LoadState.Loading
        itemView.retryButton.isVisible = loadState !is LoadState.Loading
        itemView.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): MoviesLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_footer, parent, false)
            return MoviesLoadStateViewHolder(view, retry)
        }
    }
}