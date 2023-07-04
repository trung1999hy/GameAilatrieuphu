package com.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T : ViewBinding, ITEM> constructor(
    protected var itemList: List<ITEM>,
    private val bindingClass: (LayoutInflater, ViewGroup, Boolean) -> T
) : RecyclerView.Adapter<BaseAdapter.Holder>() {

    var binding: T? = null

    init {
        update(itemList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = itemList.size
    override fun getItemViewType(position: Int): Int = position

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //inflate the bindingClass using ViewGroup binding delegation
        val viewBinding = parent.viewBinding(bindingClass)
        this.binding = viewBinding

        val viewHolder = Holder(viewBinding.root)
        val itemView = viewHolder.itemView

        itemView.tag = viewHolder
        itemView.setOnClickListener {
            val adapterPosition = viewHolder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onItemClick(itemView, adapterPosition)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.itemView.bind(item)
    }

    override fun onViewRecycled(holder: Holder) {
        super.onViewRecycled(holder)
        onViewRecycled(holder.itemView)
    }

    private fun updateAdapterWithDiffResult(result: DiffUtil.DiffResult) {
        result.dispatchUpdatesTo(this)
    }

    private fun calculateDiff(newItems: List<ITEM>): DiffUtil.DiffResult {
        return DiffUtil.calculateDiff(DiffUtilCallback(itemList, newItems))
    }

    private fun update(items: List<ITEM>) {
        updateAdapterWithDiffResult(calculateDiff(items))
    }

     fun add(item: ITEM) {
        itemList.toMutableList().add(item)
        notifyItemInserted(itemList.size)
    }

     fun remove(position: Int) {
        itemList.toMutableList().removeAt(position)
        notifyItemRemoved(position)
    }

    protected open fun View.bind(item: ITEM) {}
    protected open fun onViewRecycled(itemView: View) {}
    protected open fun onItemClick(itemView: View, position: Int) {}
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
