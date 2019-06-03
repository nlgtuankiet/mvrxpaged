package com.example.mvrxpaged.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvrxpaged.R
import com.example.mvrxpaged.ui.OnClick
import com.example.mvrxpaged.ui.main.view.FooterView
import com.example.mvrxpaged.ui.main.view.HeaderView
import com.example.mvrxpaged.ui.main.view.SeperatorView
import com.example.mvrxpaged.ui.main.view.SimpleTextView


class MainAdapter : PagedListAdapter<ItemViewModel, MainAdapter.ItemViewHolder>(ItemCallback) {

    private object ItemCallback : DiffUtil.ItemCallback<ItemViewModel>() {
        override fun areItemsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean {
            return when {
                oldItem is FooterView.Model && newItem is FooterView.Model -> oldItem.id == newItem.id
                oldItem is HeaderView.Model && newItem is HeaderView.Model -> oldItem.id == newItem.id
                oldItem is SeperatorView.Model && newItem is SeperatorView.Model -> oldItem.id == newItem.id
                oldItem is SimpleTextView.Model && newItem is SimpleTextView.Model -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean {
            return when {
                oldItem is FooterView.Model && newItem is FooterView.Model -> oldItem.content == newItem.content
                oldItem is HeaderView.Model && newItem is HeaderView.Model -> oldItem.content == newItem.content
                oldItem is SeperatorView.Model && newItem is SeperatorView.Model -> true
                oldItem is SimpleTextView.Model && newItem is SimpleTextView.Model -> oldItem.content == newItem.content
                else -> false
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HeaderView.Model -> 1
            is FooterView.Model -> 2
            is SeperatorView.Model -> 3
            else -> 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layoutRes = when (viewType) {
            1 -> R.layout.header
            2 -> R.layout.footer
            3 -> R.layout.seperator
            else -> R.layout.simple_text
        }
        val view = inflater.inflate(layoutRes, parent, false)
        return ItemViewHolder(view = view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val model = getItem(position) ?: SimpleTextView.Model(
            id = "loading $position",
            content = "loading $position",
            onClick = OnClick { }
        )

        when (model) {
            is SimpleTextView.Model -> (holder.itemView as SimpleTextView).apply {
                setContent(model.content)
                setOnClick(model.onClick)
            }
            is HeaderView.Model -> (holder.itemView as HeaderView).apply {
                setContent(model.content)
                setOnClick(model.onClick)
            }
            is FooterView.Model -> (holder.itemView as FooterView).apply {
                setContent(model.content)
                setOnClick(model.onClick)
            }
        }
    }


    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)
}