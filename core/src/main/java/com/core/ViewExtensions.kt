package com.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Create the ViewGroup binding delegation
 */
inline fun <T : ViewBinding> ViewGroup.viewBinding(binding: (LayoutInflater, ViewGroup, Boolean) -> T): T {
    return binding(LayoutInflater.from(context), this, false)
}


/**
 * Create an extension function to RecyclerView
 * And create function setup() that implement the GeneralAdapter and its viewBinding.
 */
fun <T : ViewBinding, ITEM> RecyclerView.setup(
    items: List<ITEM>,
    bindingClass: (LayoutInflater, ViewGroup, Boolean) -> T,
    bindHolder: View.(T?, ITEM) -> Unit,
    itemClick: View.(ITEM) -> Unit = {},
    manager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)
): GeneralAdapter<T, ITEM> {
    val generalAdapter by lazy {
        GeneralAdapter(items, bindingClass,
            { binding: T?, item: ITEM ->
                bindHolder(binding, item)
            }, {
                itemClick(it)
            }
        )
    }

    layoutManager = manager
    adapter = generalAdapter
    (adapter as GeneralAdapter<*, *>).notifyDataSetChanged()
    return generalAdapter
}