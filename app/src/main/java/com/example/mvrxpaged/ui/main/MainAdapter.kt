package com.example.mvrxpaged.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvrxpaged.R
import com.example.mvrxpaged.ui.OnClick
import com.example.mvrxpaged.ui.main.view.*


class MainAdapter : PagedListAdapter<ItemViewModel, MainAdapter.ItemViewHolder>(ItemCallback) {

private object ItemCallback : DiffUtil.ItemCallback<ItemViewModel>() {
    override fun areItemsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean {
        return when {
            oldItem is BannerView.Model && newItem is BannerView.Model -> oldItem.id == newItem.id
            oldItem is DealView.Model && newItem is DealView.Model -> oldItem.id == newItem.id
            oldItem is CategoryView.Model && newItem is CategoryView.Model -> oldItem.id == newItem.id
            oldItem is SeparatorView.Model && newItem is SeparatorView.Model -> oldItem.id == newItem.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean {
        return when {
            oldItem is BannerView.Model && newItem is BannerView.Model -> oldItem.content == newItem.content
            oldItem is DealView.Model && newItem is DealView.Model -> oldItem.content == newItem.content
            oldItem is CategoryView.Model && newItem is CategoryView.Model -> oldItem.content == newItem.content
            oldItem is SeparatorView.Model && newItem is SeparatorView.Model -> true
            else -> false
        }
    }
}

override fun getItemViewType(position: Int): Int {
    return when (getItem(position)) {
        is BannerView.Model -> 1
        is CategoryView.Model -> 2
        is DealView.Model -> 3
        is SeparatorView.Model -> 4
        else -> 0 // loading model
    }
}

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val layoutRes = when (viewType) {
        1 -> R.layout.banner_view
        2 -> R.layout.category_view
        3 -> R.layout.deal_view
        4 -> R.layout.separator_view
        else -> R.layout.loading_view
    }
    val view = inflater.inflate(layoutRes, parent, false)
    return ItemViewHolder(view = view)
}

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val model = getItem(position) ?: LoadingView.Model(
            id = "loading $position"
        )

        when (model) {
            is BannerView.Model -> (holder.itemView as BannerView).apply {
                setContent(model.content)
                setOnClick(model.onClick)
            }
            is DealView.Model -> (holder.itemView as DealView).apply {
                setHeader(model.content)
                setContent(model.content)
                setOnClick(model.onClick)
            }
            is CategoryView.Model -> (holder.itemView as CategoryView).apply {
                setHeader(model.content)
                setContent(model.content)
                setFooter(model.content)
                setOnClick(model.onClick)
            }
        }
    }


    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)
}