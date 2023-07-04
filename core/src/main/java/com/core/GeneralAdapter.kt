package com.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

class GeneralAdapter<T : ViewBinding, ITEM>(
    items: List<ITEM>,
    bindingClass: (LayoutInflater, ViewGroup, Boolean) -> T,
    private val bindHolder: View.(T?, ITEM) -> Unit
) : BaseAdapter<T, ITEM>(items, bindingClass) {

    private var itemClick: View.(ITEM) -> Unit = {}
    var viewBinding: T? = null

    constructor(
        items: List<ITEM>,
        bindingClass: (LayoutInflater, ViewGroup, Boolean) -> T,
        bindHolder: View.(T?, ITEM) -> Unit,
        itemViewClick: View.(ITEM) -> Unit = {}
    ) : this(items, bindingClass, bindHolder) {
        this.itemClick = itemViewClick
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (position == holder.adapterPosition) {
            this.viewBinding = binding
            holder.itemView.bindHolder(binding, itemList[position])
        }
    }

    override fun onItemClick(itemView: View, position: Int) {
        itemView.itemClick(itemList[position])
    }
}