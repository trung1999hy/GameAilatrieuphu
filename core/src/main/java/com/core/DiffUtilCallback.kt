package com.core

import androidx.recyclerview.widget.DiffUtil

internal class DiffUtilCallback<ITEM>(
    private val oldItems: List<ITEM>,
    private val newItems: List<ITEM>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size
    override fun getNewListSize(): Int = newItems.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}